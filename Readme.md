ReadMe
==========
- [ReadMe](#readme)
    - [Annotation List](#annotation-list)
        - [CheckNullAndEmpty](#checknullandempty)
        - [DateFormat](#dateformat)
        - [MergeAlias](#mergealias)
    - [Interface List](#interface-list)
        - [Convartable](#convartable)
    - [Class List](#class-list)
        - [CheckUtil](#checkutil)
        - [AnnotationUtil](#annotationutil)

### Annotation List ###
標籤列表，傳入參數如果有預設值為選填，其它為必填
* CheckNullAndEmpty
* DateFormat
* MergeAlias
##### CheckNullAndEmpty #####
> 檢查是否為空值或是Null值
- input
  - checkNull default 'Y' (是否檢查欄位為null)
  - checkEmpty default 'Y' (是否檢查欄位為空值)

##### DateFormat #####
> String 類的日期格式轉換( 支援民國年 eee(**小寫**) )
- input
  - origPatter (原始的日期格式)
  - newPattern (想轉換的日期格式)

##### MergeAlias #####
> 將兩物件欄位以及參數合併後，轉換為新物件。
- input
  - value (欄位別名，支援陣列)
  - stringToDate default '' (將字串轉換為日期格式，需輸入字串的pattern才會作用 )
  - dateToString default '' (將日期格式轉換為字串，需輸入字串的pattern才會作用 )
  - convert defalut {} (需傳入實現Convertable interface的class)

### Interface List ###
- Convertable

##### Convartable ######
搭配 MergeAlias的 convert 使用
```
convert(object) -> { return (value you want)  }
```

### Class List ###
- CheckUtil
- AnnotationUtil
##### CheckUtil #####
使用方式:搭配[CehckNullAndEmpty](#checknullandempty) Tag使用
```  
CheckUtil.check(Object) return boolean
```
檢查通過為true, 其它為 false


##### AnnotationUtil #####
使用方式:搭配[MergeAlias](#mergealias)、[DateFormat](#dateformat) Tag使用

```
AnnotationUtil.format(Object)  
```
- input
  1. 有使用Tag的Obejct
將物件中有使用DateFormat Tag的Field重新format
```
AnnotationUtil.merge(Object prime, Object other, Class<T> clazz, boolean isDb)
```
- input
  1. 合併的主物件
  2. 合併進主物件的次物件(主物件的value會被次物件取代)
  3. 需轉換成的Class型態
  4. 是否為DB物件(DB物件首字為大寫)
   
將次物件合併進主物件後，再轉換成傳入的Class 型態。如果傳入型態為DB物件(首字大寫)，則傳入第四個參數為true。