#!/bin/sh
set -eu

nohup dockerd >/dev/null 2>&1 &
exec "$@"
