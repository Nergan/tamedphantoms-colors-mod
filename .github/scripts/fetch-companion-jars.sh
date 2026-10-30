#!/usr/bin/env bash
# Скачивает jar зависимостей: Tamed Phantoms со страниц его GitHub Release,
# остальное — официальные файлы Modrinth со сверкой SHA-512.
# Аргументы: <каталог> <Tamed Phantoms> <KFF> <Patchouli NeoForge> <Fabric API>
#            <Fabric Language Kotlin> <Patchouli Fabric> <Cloth Config> <Mod Menu> <Placeholder API>
set -euo pipefail

OUT_DIR="${1:?destination directory}"
TAMED_VERSION="${2:?tamedphantoms mod_version}"
KFF_VERSION="${3:?kotlin-for-forge version_number}"
PATCHOULI_VERSION="${4:?patchouli neoforge version_number}"
FABRIC_API_VERSION="${5:?fabric-api version_number}"
FABRIC_KOTLIN_VERSION="${6:?fabric-language-kotlin version_number}"
PATCHOULI_FABRIC_VERSION="${7:?patchouli fabric version_number}"
CLOTH_VERSION="${8:?cloth-config version_number}"
MODMENU_VERSION="${9:?modmenu version_number}"
PLACEHOLDER_VERSION="${10:?placeholder-api version_number}"
USER_AGENT="${MODRINTH_USER_AGENT:-Nergan/tamedphantoms-colors-mod (https://github.com/Nergan/tamedphantoms-colors-mod)}"
TAMED_RELEASES="https://github.com/Nergan/tamedphantoms-mod/releases/download"

mkdir -p "${OUT_DIR}"

get() {
  local url="$1"
  local out="$2"
  local attempt
  for attempt in 1 2 3 4 5; do
    local code
    code="$(curl -sSL -A "${USER_AGENT}" -o "${out}" -w '%{http_code}' "${url}" || true)"
    if [[ "${code}" == "200" ]]; then
      return 0
    fi
    if [[ "${code}" == "429" || "${code}" =~ ^5 ]]; then
      echo "HTTP ${code} for ${url}, retry ${attempt}" >&2
      sleep $((attempt * 15))
      continue
    fi
    echo "HTTP ${code} for ${url}" >&2
    return 1
  done
  echo "Gave up on ${url}" >&2
  return 1
}

fetch_tamed() {
  local loader="$1"
  local name="tamedphantoms-${loader}-1.21.1-${TAMED_VERSION}.jar"
  get "${TAMED_RELEASES}/minecraft-1.21.1-${loader}/${name}" "${OUT_DIR}/${name}"
  unzip -tq "${OUT_DIR}/${name}" >/dev/null
}

fetch_modrinth() {
  local slug="$1"
  local version="$2"
  local json url filename sha512

  get "https://api.modrinth.com/v2/project/${slug}/version" /tmp/modrinth-versions.json
  json="$(cat /tmp/modrinth-versions.json)"
  url="$(echo "${json}" | jq -r --arg v "${version}" '
    first(.[] | select(.version_number == $v) | .files[] | select(.primary) | .url) // empty
  ')"
  filename="$(echo "${json}" | jq -r --arg v "${version}" '
    first(.[] | select(.version_number == $v) | .files[] | select(.primary) | .filename) // empty
  ')"
  sha512="$(echo "${json}" | jq -r --arg v "${version}" '
    first(.[] | select(.version_number == $v) | .files[] | select(.primary) | .hashes.sha512) // empty
  ')"

  if [[ -z "${url}" || -z "${filename}" || -z "${sha512}" ]]; then
    echo "No primary Modrinth file for ${slug} version ${version}" >&2
    exit 1
  fi

  get "${url}" "${OUT_DIR}/${filename}"
  echo "${sha512}  ${OUT_DIR}/${filename}" | sha512sum -c -
}

fetch_tamed neoforge
fetch_tamed fabric
fetch_modrinth "kotlin-for-forge" "${KFF_VERSION}"
fetch_modrinth "patchouli" "${PATCHOULI_VERSION}"
fetch_modrinth "fabric-api" "${FABRIC_API_VERSION}"
fetch_modrinth "fabric-language-kotlin" "${FABRIC_KOTLIN_VERSION}"
fetch_modrinth "patchouli" "${PATCHOULI_FABRIC_VERSION}"
fetch_modrinth "cloth-config" "${CLOTH_VERSION}"
fetch_modrinth "modmenu" "${MODMENU_VERSION}"
fetch_modrinth "placeholder-api" "${PLACEHOLDER_VERSION}"
