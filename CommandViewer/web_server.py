"""Command Viewer: a small CherryPy server the kitchen uses to see open orders.

GET /            regenerates index.html from the command XML files and serves it.
GET /remove      marks one order of a command as completed by removing it from
                 the XML file; the file is deleted once it has no orders left.
"""
import codecs
import os
import xml.etree.ElementTree as ET

import cherrypy

COMMANDS_DIR = os.environ.get("COMMANDS_DIR", "/srv/ftp/restaurant/commands/")
BASE_DIR = os.path.dirname(os.path.abspath(__file__))


class WebServer(object):

    @cherrypy.expose
    def index(self):
        os.system("python3 " + BASE_DIR + "/build_index.py")
        with codecs.open(BASE_DIR + "/index.html", "r") as handle:
            return handle.read()

    @cherrypy.expose
    def remove(self, commandId, orderId):
        tree = ET.parse(COMMANDS_DIR + commandId)
        root = tree.getroot()
        for order in root.findall("order"):
            if order.get("id") == orderId:
                print(order)
                root.remove(order)
        if len(root.findall("order")) == 0:
            os.remove(COMMANDS_DIR + commandId)
        else:
            tree.write(COMMANDS_DIR + commandId)

        raise cherrypy.HTTPRedirect("index")


if __name__ == "__main__":
    conf = {
        "/": {
            "tools.sessions.on": True,
            "tools.staticdir.root": BASE_DIR,
        },
        "/static": {
            "tools.staticdir.on": True,
            "tools.staticdir.dir": "resources",
        },
    }
    cherrypy.quickstart(WebServer(), "/", conf)
