#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import sys
import time
import re
import codecs
import xml.etree.ElementTree as ET
from django.template import Template,Context, Engine
path = "/srv/ftp/restaurant/commands/"
resources = os.path.dirname(os.path.abspath(__file__))+"/"

def main():

    listaFichero=obtenerFicheros()
    obtenerInformacion(listaFichero)


def obtenerInformacion(listaFichero):

    textoTemplate = cargarFicheroTexto(resources+"template.html")

    template = Engine().from_string(textoTemplate)

    
    datosFinal=Context()
    array=[]
    for nombreFichero in listaFichero:
        datosTemplate=Context()
        ordersArray=[]
        tree = ET.parse(path+nombreFichero)
        root = tree.getroot()
        datosTemplate["table"]=root.get('client-id')
        datosTemplate["name"]=nombreFichero
        datosTemplate["date"]="to do from file name"
        datosTemplate["price"]=root.find('payment-info').find('price').text
        datosTemplate["paidState"]=changeIcon(root.get('paid'))
        datosTemplate["tax"]=root.find('payment-info').find('tax').text
        orders=root.findall('order')
        for order in orders:
            orderItem = Context()
            orderItem["id"]=order.get('id')
            orderItem["name"]=order.get('name')
            orderItem["units"]=order.get('units')
            ordersArray.append(orderItem)

        datosTemplate["orders"]=ordersArray

        array.append(datosTemplate)


    datosFinal['Ficheros'] = array
    renderizarPlantilla(template,datosFinal,resources + "index.html")

def cargarFicheroTexto(ruta):
    fichero = codecs.open(ruta, 'r', encoding='utf-8')
    texto = fichero.read()
    fichero.close()
    return texto


def renderizarPlantilla(plantilla,datos,ruta):

    ficheroSalida =codecs.open(ruta, "w",encoding='utf-8')
    ficheroSalida.write(plantilla.render(datos))
    ficheroSalida.close()


def changeIcon(textosalida):
    
    if textosalida.find("true")==0:
        textosalida="done"
    elif textosalida.find("false")==0: 
        textosalida="clear"
    return textosalida

def obtenerFicheros():
    lstFiles = []
    lstDir = os.walk(path) 

    for root, dirs, files in lstDir:
       for fichero in files:
          (nombreFichero, extension) = os.path.splitext(fichero)
          if (extension == ".xml"):
            lstFiles.append(nombreFichero + extension)
    return lstFiles



main()











