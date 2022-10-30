#!/bin/sh
clear
echo "comenzando copia"
cp /etc/bind9/db.estuviani.com /etc/bind9/db.estuviani.com.copia
cp /etc/bind9/db.1.168.192 /etc/bind9/db.1.168.1.192.copia
cp /etc/postfix/main.cf /etc/postfix/main.cf.copia
cp /etc/dovecot/dovecot.conf /etc/dovecot/dovecot.conf.copia
cp /etc/dovecot/conf.d/10-ssl.conf /etc/dovecot/conf.d/10-ssl.conf.copia
cp /etc/dovecot/conf.d/10-auth.conf /etc/dovecot/conf.d/10-auth.conf.copia
cp /etc/dovecot/conf.d/10-mail.conf /etc/dovecot/conf.d/10-mail.conf.copia
echo "copias finalizadas"


