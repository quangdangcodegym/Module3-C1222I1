-- Với mỗi giáo viên, cho biết MAGV và số lượng công việc mà giáo viên đó có tham gia.

SELECT gv.MAGV, gv.HOTEN, count(*) as soluongcongviec
FROM c10_quanlydetai.thamgiadt as tgdt join giaovien as gv on tgdt.MAGV = gv.MAGV
group by MAGV;

-- Cho biết những bộ môn từ 2 giáo viên trở lên.
SELECT bm.MABM, bm.TENBM
FROM GIAOVIEN gv JOIN BOMON bm ON bm.MABM = gv.MABM
GROUP BY MABM
HAVING COUNT(*) >= 2;

-- Cho những giáo viên có lương nhỏ nhất
-- Cách 1
SELECT * 
FROM c10_quanlydetai.giaovien
WHERE LUONG = (SELECT MIN(LUONG) FROM c10_quanlydetai.giaovien);

SELECT * 
FROM c10_quanlydetai.giaovien
WHERE LUONG <= ALL (SELECT LUONG FROM c10_quanlydetai.giaovien);


