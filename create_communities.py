#!/usr/bin/env python3
"""
KAIST 커뮤니티 생성 스크립트
관리자 계정으로 로그인하여 10개의 커뮤니티를 생성합니다.
"""

import requests
import json
import base64
from io import BytesIO

try:
    from PIL import Image, ImageDraw, ImageFont
    HAS_PIL = True
except ImportError:
    HAS_PIL = False
    print("경고: PIL(Pillow)이 설치되어 있지 않습니다.")
    print("PNG 이미지 생성을 위해 'pip3 install Pillow'를 실행하세요.")

# 서버 URL
BASE_URL = "http://localhost:8091"

# 관리자 계정 정보
ADMIN_USER_ID = "admin"
ADMIN_PASSWORD = "kaist1234"

def hex_to_rgb(hex_color):
    """16진수 색상을 RGB 튜플로 변환합니다."""
    hex_color = hex_color.lstrip('#')
    return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))

def create_png_image(title, color, emoji):
    """각 커뮤니티에 맞는 PNG 이미지를 생성합니다."""
    if not HAS_PIL:
        # PIL이 없으면 간단한 base64 PNG placeholder 반환
        # 1x1 투명 PNG
        return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
    
    # 이미지 크기
    width, height = 400, 300
    
    # 이미지 생성
    img = Image.new('RGB', (width, height), color=hex_to_rgb(color))
    draw = ImageDraw.Draw(img)
    
    # 그라데이션 효과 (간단한 버전)
    for y in range(height):
        alpha = y / height
        r, g, b = hex_to_rgb(color)
        # 약간 어둡게
        r = int(r * (1 - alpha * 0.2))
        g = int(g * (1 - alpha * 0.2))
        b = int(b * (1 - alpha * 0.2))
        draw.line([(0, y), (width, y)], fill=(r, g, b))
    
    # 텍스트 추가
    try:
        # 시스템 폰트 사용 시도
        font = ImageFont.truetype("/System/Library/Fonts/Helvetica.ttc", 36)
    except:
        try:
            font = ImageFont.truetype("/System/Library/Fonts/Arial.ttf", 36)
        except:
            # 기본 폰트 사용
            font = ImageFont.load_default()
    
    # 텍스트 크기 계산 (PIL 버전 호환성)
    try:
        bbox = draw.textbbox((0, 0), title, font=font)
        text_width = bbox[2] - bbox[0]
        text_height = bbox[3] - bbox[1]
    except AttributeError:
        # 오래된 PIL 버전용
        bbox = draw.textsize(title, font=font)
        text_width, text_height = bbox
    
    # 텍스트 중앙 배치
    text_x = (width - text_width) // 2
    text_y = (height - text_height) // 2 - 20
    
    # 텍스트 그림자 효과
    draw.text((text_x + 2, text_y + 2), title, font=font, fill=(0, 0, 0, 128))
    draw.text((text_x, text_y), title, font=font, fill=(255, 255, 255))
    
    # 이모지 추가 (가능한 경우)
    if emoji:
        try:
            emoji_font = ImageFont.truetype("/System/Library/Fonts/Apple Color Emoji.ttc", 80)
            try:
                emoji_bbox = draw.textbbox((0, 0), emoji, font=emoji_font)
                emoji_width = emoji_bbox[2] - emoji_bbox[0]
            except AttributeError:
                emoji_width, _ = draw.textsize(emoji, font=emoji_font)
            emoji_x = (width - emoji_width) // 2
            emoji_y = text_y - 100
            draw.text((emoji_x, emoji_y), emoji, font=emoji_font)
        except:
            pass
    
    # PNG를 base64로 인코딩
    buffer = BytesIO()
    img.save(buffer, format='PNG')
    img_bytes = buffer.getvalue()
    base64_png = base64.b64encode(img_bytes).decode('utf-8')
    
    # data:image/png;base64, 형식으로 반환 (postman.json 예제와 동일)
    return f"data:image/png;base64,{base64_png}"

# 커뮤니티 정보 리스트 (이미지 포함)
# 참고: "동아리" 단어는 제거되었습니다.
COMMUNITIES = [
    {
        "title": "KAIST 축구",
        "summary": "KAIST 학생들을 위한 축구 모임입니다. 매주 정기 모임과 친선 경기를 통해 축구 실력을 향상시키고 친목을 도모합니다.",
        "color": "#2E7D32",
        "emoji": "⚽"
    },
    {
        "title": "KAIST 밴드",
        "summary": "음악을 사랑하는 KAIST 학생들의 밴드 모임입니다. 정기 공연과 연습을 통해 음악적 재능을 발휘하고 즐거운 시간을 보냅니다.",
        "color": "#7B1FA2",
        "emoji": "🎸"
    },
    {
        "title": "KAIST 봉사",
        "summary": "지역사회와 소외계층을 위한 봉사활동을 진행하는 모임입니다. 정기적인 봉사활동을 통해 나눔의 가치를 실천합니다.",
        "color": "#F57C00",
        "emoji": "🤝"
    },
    {
        "title": "KAIST 프로그래밍",
        "summary": "프로그래밍과 개발에 관심이 있는 학생들을 위한 모임입니다. 프로젝트 협업, 스터디, 해커톤 참여 등을 통해 실력을 키웁니다.",
        "color": "#1976D2",
        "emoji": "💻"
    },
    {
        "title": "KAIST 사진",
        "summary": "사진 촬영과 편집을 즐기는 학생들의 모임입니다. 정기적인 외출 촬영과 작품 전시회를 통해 사진 실력을 향상시킵니다.",
        "color": "#424242",
        "emoji": "📷"
    },
    {
        "title": "KAIST 독서",
        "summary": "책을 읽고 토론하는 것을 좋아하는 학생들의 모임입니다. 매월 선정 도서를 읽고 정기 모임에서 깊이 있는 토론을 진행합니다.",
        "color": "#5D4037",
        "emoji": "📚"
    },
    {
        "title": "KAIST 댄스",
        "summary": "댄스와 무용을 사랑하는 학생들의 모임입니다. 다양한 장르의 춤을 배우고 정기 공연을 통해 실력을 뽐냅니다.",
        "color": "#C2185B",
        "emoji": "💃"
    },
    {
        "title": "KAIST 영화",
        "summary": "영화 감상과 제작에 관심이 있는 학생들의 모임입니다. 정기적인 영화 상영회와 단편 영화 제작 활동을 진행합니다.",
        "color": "#1A237E",
        "emoji": "🎬"
    },
    {
        "title": "KAIST 요리",
        "summary": "요리와 베이킹을 즐기는 학생들의 모임입니다. 다양한 요리를 배우고 함께 만들어 먹으며 즐거운 시간을 보냅니다.",
        "color": "#E64A19",
        "emoji": "🍳"
    },
    {
        "title": "KAIST 체스",
        "summary": "체스와 보드게임을 즐기는 학생들의 모임입니다. 정기적인 대회와 연습을 통해 실력을 향상시키고 친목을 도모합니다.",
        "color": "#1B5E20",
        "emoji": "♟️"
    }
]

def login():
    """관리자로 로그인하고 토큰을 반환합니다."""
    print("관리자 로그인 중...")
    
    url = f"{BASE_URL}/auth/authenticate"
    payload = {
        "userId": ADMIN_USER_ID,
        "upassword": ADMIN_PASSWORD
    }
    
    try:
        response = requests.post(url, json=payload, headers={
            "Content-Type": "application/json;charset=UTF-8"
        })
        
        if response.status_code == 200:
            data = response.json()
            token = data.get("token")
            if token:
                print(f"로그인 성공! 토큰: {token[:20]}...")
                return token
            else:
                print("로그인 응답에 토큰이 없습니다.")
                print(f"응답: {response.text}")
                return None
        else:
            print(f"로그인 실패! 상태 코드: {response.status_code}")
            print(f"응답: {response.text}")
            return None
    except Exception as e:
        print(f"로그인 중 오류 발생: {e}")
        return None

def create_community(token, title, summary, image_str):
    """커뮤니티를 생성합니다. imageStr은 data:image/png;base64, 형식이어야 합니다."""
    print(f"커뮤니티 생성 중: {title}")
    
    # imageStr이 올바른 형식인지 확인
    if not image_str.startswith("data:image/png;base64,"):
        print(f"  경고: imageStr이 올바른 형식이 아닙니다. 수정 중...")
        if image_str.startswith("data:image/"):
            # 이미 data:image/로 시작하면 base64, 부분만 확인
            pass
        else:
            # base64 문자열만 있으면 prefix 추가
            image_str = f"data:image/png;base64,{image_str}"
    
    url = f"{BASE_URL}/community/create"
    payload = {
        "title": title,
        "summary": summary,
        "imageStr": image_str
    }
    
    headers = {
        "Content-Type": "application/json;charset=UTF-8",
        "Authorization": f"Bearer {token}"
    }
    
    try:
        response = requests.post(url, json=payload, headers=headers)
        
        if response.status_code == 200:
            print(f"✓ 성공: {title} (이미지 포함)")
            return True
        else:
            print(f"✗ 실패: {title} (상태 코드: {response.status_code})")
            print(f"  응답: {response.text}")
            return False
    except Exception as e:
        print(f"✗ 오류 발생: {title} - {e}")
        return False

def main():
    """메인 함수"""
    print("=" * 50)
    print("KAIST 커뮤니티 생성 스크립트")
    print("=" * 50)
    print()
    
    # 로그인
    token = login()
    if not token:
        print("로그인에 실패했습니다. 스크립트를 종료합니다.")
        return
    
    print()
    print("=" * 50)
    print("커뮤니티 생성 시작")
    print("=" * 50)
    print()
    
    # 커뮤니티 생성
    success_count = 0
    fail_count = 0
    
    for i, community in enumerate(COMMUNITIES, 1):
        print(f"[{i}/{len(COMMUNITIES)}] ", end="")
        
        # PNG 이미지 생성 및 base64 인코딩 (data:image/png;base64, 형식)
        image_str = create_png_image(
            community["title"], 
            community["color"], 
            community.get("emoji", "")
        )
        
        # 이미지가 올바르게 생성되었는지 확인
        if not image_str or len(image_str) < 100:
            print(f"  경고: 이미지 생성에 문제가 있을 수 있습니다.")
        
        if create_community(token, community["title"], community["summary"], image_str):
            success_count += 1
        else:
            fail_count += 1
        print()
    
    # 결과 출력
    print("=" * 50)
    print("생성 완료!")
    print(f"성공: {success_count}개")
    print(f"실패: {fail_count}개")
    print("=" * 50)

if __name__ == "__main__":
    main()

