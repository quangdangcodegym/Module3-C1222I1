/**
14/03/2023

Toán tử like được sử dụng trong mệnh đề where để tìm kiếm một mẫu chỉ định trong cột
có hai ký tự đại diện thường được sử dụng cùng toán từ like:
- % đại diện cho không, một hoặc nhiều ký tự
- _ đại diện ký tự đơn
- % và _ có thể kết hơp cùng nhau

SELECT * FROM c9_quanlydetai.bomon where TENBM like '____lý%';
    
+ Toán tử EXISTS:
 	- Toán tử EXISTS dùng để kiểm tra xem có tồn tại bất kì bản ghi nào trong một bảng được chỉ định 
	thỏa mãn điều kiện của một truy vấn con hay không 
SELECT (EXISTS (SELECT * FROM BOMON WHERE MABM = 'AAA'));

+ Toán tử IN:
	- Toán tử IN dùng để kiểm tra xem giá trị của một trường có nằm trong danh sách các giá trị 
	được chỉ định hay không
SELECT * FROM c9_quanlydetai.bomon where MABM IN ("CNTT", "HHC");

SELECT * FROM c9_quanlydetai.bomon where MABM IN (
	SELECT MABM FROM c9_quanlydetai.bomon where MABM = "CNTT" OR MABM =  "JAVA"
    );
+ Toán tử ANY và ALL:
	- Toán tử ANY và ALL dùng để so sánh một giá trị với một tập hợp giá trị được trả về từ một truy vấn con.
	- Toán tử ANY kiểm tra xem giá trị có bằng bất kì giá trị nào trong tập hợp hay không
	- Toán tử ALL thì kiểm tra xem giá trị có bằng tất cả các giá trị trong tập hợp hay không
SELECT * 
FROM c9_quanlydetai.congviec
WHERE SOTT > ANY (select 2 as ID union select 6 as NAME union select 8 as ID);

+ Toán tử UNION:
	- Toán tử UNION được sử dụng trong mySQL để kết hợp các kết quả của hai hoặc nhiều câu lệnh SELECT 
	khác nhau thành một kết quả duy nhất. Các câu lệnh SELECT phải trả về cùng số lượng và cùng kiểu dữ liệu 
	các cột được chọn 
    
    select 2 as ID union select 6 as `NAME` union select MABM from bomon;

select 2 as ID union select 6 as `NAME` union select MABM from bomon;


	select 2 as ID;
	select 6 as `NAME`;
	select MABM, PHONG from bomon;
**/
	