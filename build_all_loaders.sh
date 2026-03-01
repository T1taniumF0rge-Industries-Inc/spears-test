#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
DIST_DIR="$ROOT_DIR/dist"
TMP_DIR="$ROOT_DIR/.tmp_build"

python - <<'PY'
import shutil, os
for p in ['dist', '.tmp_build']:
    if os.path.exists(p):
        shutil.rmtree(p)
os.makedirs('dist', exist_ok=True)
os.makedirs('.tmp_build', exist_ok=True)
PY

# NeoForge artifact (primary)
cp "$ROOT_DIR/jars/neoforge/spears-1.0.0-neoforge-1.21.8.jar" "$DIST_DIR/spears-1.0.1-neoforge-1.21.8.jar"

# Port resource/data bundles for Fabric/Forge/Quilt.
# To keep PRs text-only, loader ports do not store binary textures in-repo.
# We inject textures from the canonical NeoForge assets during packaging.
for LOADER in fabric forge quilt; do
  SRC="$ROOT_DIR/ports/$LOADER/src/main/resources"
  STAGE="$TMP_DIR/$LOADER"
  OUT="$DIST_DIR/spears-1.0.1-$LOADER-1.21.8.zip"

  mkdir -p "$STAGE"
  cp -r "$SRC"/. "$STAGE"/
  mkdir -p "$STAGE/assets/spears/textures/item"
  cp "$ROOT_DIR/neoforge/assets/spears/textures/item/"*.png "$STAGE/assets/spears/textures/item/"

  (
    cd "$STAGE"
    zip -qr "$OUT" .
  )
done

echo "Built artifacts:"
ls -1 "$DIST_DIR"
