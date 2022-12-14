# wifi

내 위치 기반 공공 와이파이 정보를 제공하는 웹 서비스



## 활용 기술

- JSP (JDK1.8)
- Maven
- MariaDB
- Lombok
- okhttp3
- gson



## 구현

#### Open API 와이파이 정보 가져오기
1. Open API 와이파이 정보 가져오기 시, load-wifi.jsp를 통해 ApiExplorer 클래스의 loadWifi 함수 호출
2. Open API에서 약 14985개의 WIFI 정보 load

#### 내 위치 가져오기를 한 후에 근처 WIFI 정보 보기
1. 내 위치 가져오기(Javascript)를 통해, 현재 나의 LAT과 LNT를 얻기
2. 근처 WIFI 정보 보기 시, WifiDAO 클래스의 selectWifi 함수 호출 + MyWifiDAO 클래스의 insertMyWifi 함수 호출
3. 내 위치 정보에서 가까운 위치에 있는 와이파이 정보 20개 보여주기 + 위치 히스토리 저장

#### 위치 히스토리 목록
1. 위치 히스토리 목록 보기 시, MyWifiDAO 클래스의 selectMyWifi 함수 호출
2. DB에 저장된 위치 히스토리 목록 load
3. 위치 히스토리 삭제 시, delete-history.jsp를 통해 MyWifiDAO 클래스의 deleteMyWifi 함수 호출
4. DB에 지정한 위치 히스토리 삭제



## feedback

1. 클래스명은 파스칼 표기법으로, 변수나 함수명은 카멜표기법으로 작성
(MyWifiDTO => MyWifiDto, WifiDTO => WifiDto)
2. 디비 연결하는 부분은 기본클래스나 공통클래스로 작성해서 처리
3. selectWifi부분에 대해서 전체를 가져와서 정렬하는 부분 보다는, select할때 이미 조건에 맞는 데이터중에서 가져오도록 처리
4. Service와 Dao 차이 이해
(보통 한개의 업무처리는 Service 형태로 호출이되고, 이 업무에서 데이터처리를 위해서 Dao가 사용)



