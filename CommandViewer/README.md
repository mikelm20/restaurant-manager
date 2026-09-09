# Command Viewer

A small web page for the kitchen that lists the open commands (orders) written
by the ordering devices as XML files, and lets the staff tick off each dish as
it is served.

## Install

```sh
pip3 install CherryPy Django
```

## Run

```sh
COMMANDS_DIR=/srv/ftp/restaurant/commands/ python3 web_server.py
```

Then open http://localhost:8080/. The page refreshes itself every 10 seconds.
`COMMANDS_DIR` must point at the folder where the command XML files are
dropped (see the root README for the shared FTP layout).
