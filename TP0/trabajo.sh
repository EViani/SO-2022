#!/bin/sh
clear
archivo="p-usem.dat"
Narchivo="./$archivo"
echo "-----------------------------------------------------"
echo " 1) Ingrese una línea de texto para probar el código "
echo " 2) Compruebe que el texto que Ud. ingresó figure en "
echo "
el archivo : $archivo
"
echo "-----------------------------------------------------"
read Param
./usem a $Narchivo $Param
echo "-------------------< Trabajo Realizado >-------------"