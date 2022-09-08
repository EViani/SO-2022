/****************************************************************************/
/* - usem.c - Utilitario básico para semáforos IPC System V -
*/
/****************************************************************************/

#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <errno.h> /*Libreria agregadad*/

#define SEM_MAX_RECURSO 1 /* Valor inicial de todos los semáforos */
#define INI 0 /* Índice del primer semáforo */
/*Constantes agregadas*/
#define SEMMNI 128 /* máximo número de identificadores de conjuntos*/
#define SEMMSL 32 /* máximo número de semáforos por identificador*/
#define SEMOPM 32 /* máximo número de operaciones por llamada semop*/
#define SEMVMX 32767 /* máximo valor por semáforo*/


void abrir_sem(int *sid, key_t clave);
void crear_sem(int *sid, key_t clave, int idx);
void bloquear_sem(int clave, int idx);
void zero_espera_sem(int clave, int idx);
void desbloquear_sem(int clave, int idx);
void remover_sem(int sid);
void uso(void);

/*Estructura semun agregada*/
/* arg para la llamada del sistema semctl*/
union semun {
    int val;/* valor para SETVAL*/
    struct semid_ds *buf;/* buffer para IPC_STAT e IPC_SET*/
    ushort*array; /* array para GETALL y SETALL*/
    struct seminfo *__buf; /* buffer para IPC_INFO*/
    void *__pad;
};


int main(int argc, char *argv[])
{
    key_t clave;
    int semset_id;
    FILE *fptr;

    if(argc == 1)
        uso();
    /* Crea una clave única mediante ftok() */
    clave = ftok(".", 'u');
    switch(tolower(argv[1][0]))
        {
        case 'c': if(argc != 2)
            uso();
            crear_sem(&semset_id, clave, 1);
            break;
        case 't': abrir_sem(&semset_id, clave);
            bloquear_sem(semset_id, INI);
            getchar();
            desbloquear_sem(semset_id, INI);
            break;
        case 'e': abrir_sem(&semset_id, clave);
             zero_espera_sem(semset_id, INI);
            break;
        case 'b': abrir_sem(&semset_id, clave);
            remover_sem(semset_id);
            break;
        case 'a': abrir_sem(&semset_id, clave);
            bloquear_sem (semset_id, INI);
            if ((fptr = fopen(argv[2], "a")) == NULL)
                exit (-1);
            else
            {
                fprintf(fptr, "%s\n", argv[3]);
                fclose(fptr);
            }
            desbloquear_sem (semset_id, INI);
            break;
        default: uso();
        }
    return(0);
}

void abrir_sem(int *sid, key_t clave)
{
    /* Abre el conjunto de semáforos*/
    if((*sid = semget(clave, 0, 0666)) == -1)
    {
        printf("El conjunto de semáforos NO existe!\n");
        exit(1);
    }
}

void crear_sem(int *sid, key_t clave, int cantidad)
{
    int cntr;
    union semun semopciones;
    if(cantidad > SEMMSL)
    {
        printf("ERROR : cant. máx. de sem. en el conjunto es %d\n", SEMMSL);
        exit(1);
    }
    printf("Creando nuevo conjunto IPC con %d semáforo(s)...\n",cantidad);

    if((*sid = semget(clave, cantidad, IPC_CREAT|IPC_EXCL|0666)) == -1)
    {
        fprintf(stderr, "Ya existe un conjunto con esta clave!\n");
        exit(1);
    }
    printf("Nuevo conjunto IPC de sem. creado con éxito\n");
    semopciones.val = SEM_MAX_RECURSO;

    /* Inicializa todos los semáforos del conjunto */
    for(cntr=0; cntr<cantidad; cntr++)
        semctl(*sid, cntr, SETVAL, semopciones);
}

void bloquear_sem(int sid, int idx)
{
    struct sembuf sembloqueo={ 0, -1, SEM_UNDO};
    sembloqueo.sem_num = idx;
    if((semop(sid, &sembloqueo, 1)) == -1)
    {
        fprintf(stderr, "El Bloqueo falló\n");
        exit(1);
    }
}

void zero_espera_sem(int sid, int idx)
{
    struct sembuf esperazero={ 0, 0, SEM_UNDO};
    esperazero.sem_num = idx;
    printf("Proceso a la ESPERA de valor CERO en semáforo IPC...\n");
    if((semop(sid, &esperazero, 1)) == -1)
    {
        fprintf(stderr, "La Espera NO pudo establecerse \n");
        fprintf(stderr, "Valor de ERRNO : %d \n", errno);
        exit(1);
    }
    printf("ESPERA concluída. Terminación del proceso.\n");
}

void desbloquear_sem(int sid, int idx)
{
    struct sembuf semdesbloqueo={ 0, 1, SEM_UNDO};
    semdesbloqueo.sem_num = idx;
    /* Intento de desbloquear el semáforo */
    if((semop(sid, &semdesbloqueo, 1)) == -1)
    {
        fprintf(stderr, "El Desbloqueo falló\n");
        exit(1);
    }
}

void remover_sem(int sid)
{
    semctl(sid, 0, IPC_RMID, 0);
    printf("Conjunto de semáforos eliminado\n");
}

void uso(void)
{
    fprintf(stderr, " - usem - Utilitario básico para semáforos IPC\n");
    fprintf(stderr, "    USO : usem    (c)rear    \n");
    fprintf(stderr, "    (t)oma recurso compartido    \n");
    fprintf(stderr, "    (e)spera IPC de valor cero    \n");
    fprintf(stderr, "    (b)orrar    \n");
    fprintf(stderr, "    (a)gregar <PATH-DB> <LINE>    \n");
    exit(1);
}

/**************************** fín de usem.c *********************************/