#!/bin/bash

# Python 스크립트를 사용하는 것을 권장합니다.
# 이미지가 포함된 커뮤니티 생성을 위해서는 Python 스크립트를 사용하세요.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

if command -v python3 &> /dev/null; then
    echo "Python 스크립트를 실행합니다..."
    python3 "${SCRIPT_DIR}/create_communities.py"
else
    echo "Python3가 설치되어 있지 않습니다."
    echo "Python 스크립트를 사용하려면 Python3를 설치하세요."
    exit 1
fi

