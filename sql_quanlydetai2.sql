/**
-- Cho biết sốSELECT year(NGAYKT) as nam, count(*) as soluong
FROM c9_quanlydetai.detai 
group by year(NGAYKT)
order by nam;

**/

-- Với mỗi giáo viên, cho biết MAGV và số lượng công việc mà giáo viên đó có tham gia.
SELECT MAGV, COUNT(MADT)
FROM c9_quanlydetai.thamgiadt
group by MAGV;

-- Nâng cấp1:
select gv.MAGV, ifnull(tb_count.socongviec, 0) as socongviec
from giaovien gv
left join (
	SELECT MAGV, COUNT(MADT) as socongviec
	FROM c9_quanlydetai.thamgiadt
	group by MAGV
	) as tb_count on gv.MAGV = tb_count.MAGV;
-- Nâng cấp 2:
SELECT gv.MAGV, (
		SELECT count(*) 
		FROM c9_quanlydetai.thamgiadt
		where MAGV = gv.MAGV
	) as socongviec
FROM c9_quanlydetai.giaovien as gv;


-- Câu A7: Cho những giáo viên có lương cao hơn tất cả các giáo viên của bộ môn HTTT
select *
from giaovien as gv
where gv.LUONG >= ALL ( select LUONG from giaovien where MABM = 'HTTT');
    
-- Câu A22: Cho tên biết đề tài có đông giáo viên tham gia nhất
-- Cách 1:
select MADT, count(distinct magv) as sl_thamgia
from thamgiadt
group by madt
having sl_thamgia >= all ( select count(distinct magv) as sl_thamgia
							from thamgiadt
							group by madt
                        );
-- Cách 2:
select MADT, count(distinct magv) as sl_thamgia
from thamgiadt
group by madt
having sl_thamgia = (
		select max(temp.sl_thamgia) as max
		from (
				select count(distinct magv) as sl_thamgia
				from thamgiadt
				group by madt
			) as temp
		)
    

