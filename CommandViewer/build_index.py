#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Render the open commands (orders) found in the FTP folder into index.html.

Each command is an XML file written by the ordering devices. This script reads
every *.xml file in COMMANDS_DIR, fills template.html with the Django template
engine and writes the result next to this script as index.html.
"""
import codecs
import os
import xml.etree.ElementTree as ET

from django.template import Context, Engine

COMMANDS_DIR = os.environ.get("COMMANDS_DIR", "/srv/ftp/restaurant/commands/")
RESOURCES = os.path.dirname(os.path.abspath(__file__)) + "/"


def main():
    file_list = list_command_files()
    build_index(file_list)


def build_index(file_list):
    template_text = read_text_file(RESOURCES + "template.html")
    template = Engine().from_string(template_text)

    context = Context()
    commands = []
    for file_name in file_list:
        command = Context()
        orders = []
        tree = ET.parse(COMMANDS_DIR + file_name)
        root = tree.getroot()
        command["table"] = root.get("client-id")
        command["name"] = file_name
        command["date"] = "to do from file name"
        command["price"] = root.find("payment-info").find("price").text
        command["paidState"] = paid_icon(root.get("paid"))
        command["tax"] = root.find("payment-info").find("tax").text
        for order in root.findall("order"):
            item = Context()
            item["id"] = order.get("id")
            item["name"] = order.get("name")
            item["units"] = order.get("units")
            orders.append(item)

        command["orders"] = orders
        commands.append(command)

    context["files"] = commands
    render_template(template, context, RESOURCES + "index.html")


def read_text_file(file_path):
    with codecs.open(file_path, "r", encoding="utf-8") as handle:
        return handle.read()


def render_template(template, data, file_path):
    with codecs.open(file_path, "w", encoding="utf-8") as output_file:
        output_file.write(template.render(data))


def paid_icon(paid):
    """Map the paid attribute to the Material icon shown in the table."""
    if paid.find("true") == 0:
        return "done"
    if paid.find("false") == 0:
        return "clear"
    return paid


def list_command_files():
    files = []
    for _root, _dirs, names in os.walk(COMMANDS_DIR):
        for name in names:
            (_base, extension) = os.path.splitext(name)
            if extension == ".xml":
                files.append(name)
    return files


main()
