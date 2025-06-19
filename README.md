![image](https://github.com/user-attachments/assets/20c25975-89fa-4275-aa5b-4e3dbb3cbdbd)## ddl :
CREATE MEMORY TABLE "PUBLIC"."CURRENCY"(

    "CODE" CHARACTER VARYING(5) NOT NULL,

    "CREATED_TIME" TIMESTAMP,

    "EXCHANGE_RATE" NUMERIC(10, 4) NOT NULL,

    "NAME" CHARACTER VARYING(50) NOT NULL,

    "SYMBOL" CHARACTER VARYING(10),

    "UPDATE_TIME" TIMESTAMP

);

## 內容介紹

* 利用Spring Cloud OpenFeign協助串接coindesk API，以利後續轉換資料(CoinDeskClient.class部分)

* 使用CoinDeskController 呼叫CoinDeskService的方法取得轉換後資料
時間部分取updatedISO轉換成yyyy/MM/dd HH:mm:ss形式
幣別部分原來為map形式，轉為List形式回傳
中文名稱因coindesk API未提供，所以整理成Enum部分判斷(CoinDeskConst.class)，若有取不到值的部分會回傳空值，可再到enum調整
回傳如下:
{
  "updatedTime": "2024/09/02 07:07:20",
  "chartName": "Bitcoin",
  "currencyList": [
    {
      "code": "USD",
      "chineseName": "美金",
      "symbol": "&#36;",
      "rate": "57,756.298",
      "description": "United States Dollar",
      "rateFloat": 57756.2984
    },
    {
      "code": "GBP",
      "chineseName": "英鎊",
      "symbol": "&pound;",
      "rate": "43,984.02",
      "description": "British Pound Sterling",
      "rateFloat": 43984.0203
    },
    {
      "code": "EUR",
      "chineseName": "歐元",
      "symbol": "&euro;",
      "rate": "52,243.287",
      "description": "Euro",
      "rateFloat": 52243.2865
    }
  ]
}

*CRUD部分
*於h2資料庫先建立一個table currency

*使用CurrencyController 呼叫CurrencyService的方法取得轉換後資料

*查詢全部: 直接使用GETMAPPING 取得所有資料後轉換成RESPONSE回傳
*查詢單一條件:使用POSTMAPPING 以CODE查詢資料庫資料，有對欄位做檢核，及防呆機制
*新增資料:使用POSTMAPPING將所有REQUEST資料存進資料庫，有對REQUEST欄位做檢核，且若CODE原先已存在則不讓進行調整且報錯，新增完畢會回傳本次新增內容
*修改資料:使用POSTMAPPING，因REQUEST是使用全部所有的資料，所以欄位檢核目前與新增相同，若CODE並不存在資料表裡會報錯，修改完畢會回傳本次修改內容
*刪除資料:使用POSTMAPPING，以CODE查詢資料庫資料，有資料存在則進行刪除，但若不存在資料表裡則會報錯

*ErrorHandlerController 例外處理
因CRUD有兩大項錯誤: 1. 資料不存在資料表裡 2. 資料已存在資料表裡，所以客製兩項拋錯處理回傳錯誤訊息(CurrencyException、CurrencyAlreadyExistsException)
REQUEST部分有使用@Valid，所以使用MethodArgumentNotValidException將錯誤訊息客制處理

*單元測試
*針對資料轉換相關邏輯作單元測試
1.先將原先要呼叫coindesk API取得的資料改用MOCK自行建立假資料(測試內容大致與coindesk API內容幾乎相同)
2.執行coinDeskService的方法
3.進行內容的驗證
a. 確認執行完資料不為空
b. 確認資料都相符
c. 確認List資料數量及裡面的資料都相同
d. 驗證 mock 只被呼叫一次
e. 確保 mock 沒有被多餘呼叫

*功能測試
*利用TestRestTemplate 進行spring boot 功能測試，啟動內建伺服器進行測試

*CoinDeskControllerTest
1. 執行 GET 請求,取回資料
2. 驗證http狀態是否為200
3. 驗證返回資料欄位
4. 回傳result

*CurrencyControllerTest
*因方法比較多所以有用oder控制順序以方便測試

1. 新增
   a. 執行 POST 請求,取回資料
   b. 驗證http狀態是否為201
   c. 驗證返回資料
   d. 回傳result
2. 查詢全部
   a. 執行 GET 請求,取回資料
   b. 驗證http狀態是否為200
   c. 驗證返回資料確實有值
   d. 回傳result
3. 查詢單一資料
   a. 以新增的資料執行 POST 請求,取回資料
   b. 驗證http狀態是否為200
   c. 驗證返回資料
   d. 回傳result
4. 修改資料
   a. 以新增的資料修改執行 POST 請求,取回資料
   b. 驗證http狀態是否為200
   c. 驗證返回資料
   d. 回傳result
5. 刪除資料
   a. 以新增的資料刪除執行 POST 請求,取回資料
   b. 驗證http狀態是否為204
   c. 回傳result
6. 報錯模擬- 新增資料rate format錯誤為例
   a. 執行 POST 請求,取回錯誤訊息
   b. 驗證http狀態是否為400
   c. 驗證返回錯誤訊息是否為預期
   d. 回傳result
7. 報錯模擬- 查詢單一資料code notBlank 為例
   a. 執行 POST 請求,取回錯誤訊息
   b. 驗證http狀態是否為400
   c. 驗證返回錯誤訊息是否為預期
   d. 回傳result

   
