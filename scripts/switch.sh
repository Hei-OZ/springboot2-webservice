#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy()
{
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 Port: $IDLE_PORT"
  echo "> Port 전환"
  # 하나의 문장을 만들어 | (파이프라인)으로 넘겨주기 위해 echo 사용. Nginx 가 사용할 프록시 주소 생성(쌍따옴표 이용. 이용 안 하면 $service_url 변수를 찾게 됨)
  # 앞에서 넘겨준 문장을 service-url.inc 에 덮어 씀
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  echo "> Nginx Reload"
  sudo service nginx reload # Nginx 설정 다시 불러오기. 중요설정 반영은 restart 사용.
}