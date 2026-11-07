"""Set the Modrinth project icon from a PNG file.

Usage: set-modrinth-icon.py <project-id> <png-file>
MODRINTH_TOKEN must be set. The token is not printed.
"""

import os
import sys
import urllib.error
import urllib.request

project = sys.argv[1]
path = sys.argv[2]
token = os.environ["MODRINTH_TOKEN"]
with open(path, "rb") as handle:
    payload = handle.read()
limit = 256 * 1024
if len(payload) > limit:
    raise SystemExit(f"{path} is {len(payload)} bytes; Modrinth icons must be at most 256 KiB")
request = urllib.request.Request(
    f"https://api.modrinth.com/v2/project/{project}/icon?ext=png",
    data=payload,
    headers={
        "Authorization": token,
        "User-Agent": "Nergan/minecraft-mods",
        "Content-Type": "image/png",
    },
    method="PATCH",
)
try:
    with urllib.request.urlopen(request) as response:
        print("icon updated", project, response.status)
except urllib.error.HTTPError as error:
    print(f"HTTP {error.code} PATCH https://api.modrinth.com/v2/project/{project}/icon")
    print(error.read().decode("utf-8", "replace")[:2000])
    raise SystemExit(1)
