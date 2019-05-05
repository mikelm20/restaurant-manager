import cherrypy
import codecs
import os,time
import sys
import os.path
from cherrypy.lib import static
import xml.etree.ElementTree as ET

from tempfile import mkstemp
from os import fdopen, remove

path = "/srv/ftp/restaurant/commands/"


def printear(value):
    print(value)
class WebServer(object):

    @cherrypy.expose
    def index(self): 
        os.system("python3 "+os.path.dirname(os.path.abspath(__file__))+"/combineresultsonHTML.py")
        fichero = codecs.open(os.path.dirname(os.path.abspath(__file__)) +"/index.html",'r')
        html = fichero.read()
        fichero.close()
        
        return html

    @cherrypy.expose
    def remove(self,commandId,orderId):
        tree = ET.parse(path+commandId)
        root = tree.getroot()
        for order in root.findall('order'):
            if order.get('id') == orderId:
                printear(order)
                root.remove(order)
        if len(root.findall('order'))==0:
            os.remove(path+commandId)

        else:     
            tree.write(path+commandId)

        raise cherrypy.HTTPRedirect("index")


if __name__ == '__main__':
    conf = {
    '/': {
        'tools.sessions.on': True,
        'tools.staticdir.root': './'
        },
    '/static': {
        'tools.staticdir.on': True,
        'tools.staticdir.dir': 'resources'
        },
    
    }
    cherrypy.quickstart(WebServer(),'/', conf)