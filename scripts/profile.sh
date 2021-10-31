#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음

function find_idle_profile()
{
  RESONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile) # 현재 url 정상 수행중인지 확인.
  # 응답값을 HttpStatus 로 받기에 200이 정상이고 이외 400~503 사이로 에러 발생하면 real2를 현재 profile로 사용

  if [ ${RESONSE_CODE} -ge 400 ]  # 400 보다 크면(즉 40x, 50x 에러 모두 포함)
  then
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=real1
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile 의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"   # bash 스크립트는 값 반환 기능이 없어 제일 마지막줄에 echo 로 출력 후 클라이언트에서 그 값을 $(find_idle_profile) 사용
  else
    echo "8082"   # 따라서 중간에 echo 를 사용하면 안 됨
  fi
}