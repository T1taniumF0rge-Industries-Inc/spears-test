#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
DIST_DIR="$ROOT_DIR/dist"
TMP_DIR="$ROOT_DIR/.tmp_build"
VERSION="2.0.0"
MC_VERSION="1.21.8"

python - <<'PY'
import shutil, os
for p in ['dist', '.tmp_build']:
    if os.path.exists(p):
        shutil.rmtree(p)
os.makedirs('dist', exist_ok=True)
os.makedirs('.tmp_build', exist_ok=True)
PY

# NeoForge artifact (primary): package directly from neoforge tree so metadata/class updates are included
(
  cd "$ROOT_DIR/neoforge"
  jar --create --file "$DIST_DIR/spears-${VERSION}-neoforge-${MC_VERSION}.jar" .
)

# Fabric / Forge / Quilt jars built from their resource roots + injected canonical textures.
for LOADER in fabric forge quilt; do
  SRC="$ROOT_DIR/$LOADER/src/main/resources"
  STAGE="$TMP_DIR/$LOADER"
  OUT="$DIST_DIR/spears-${VERSION}-$LOADER-${MC_VERSION}.jar"

  mkdir -p "$STAGE"
  cp -r "$SRC"/. "$STAGE"/
  mkdir -p "$STAGE/assets/spears/textures/item"
  cp "$ROOT_DIR/neoforge/assets/spears/textures/item/"*.png "$STAGE/assets/spears/textures/item/"

  (
    cd "$STAGE"
    jar --create --file "$OUT" .
  )
done

echo "Built artifacts:"
ls -1 "$DIST_DIR"
