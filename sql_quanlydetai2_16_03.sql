-- Cho biết bộ môn (MABM) có đông giáo viên nhất
SELECT MABM,count(*) as tong
FROM c12_quanlydetai.giaovien
group by MABM
having tong >=  all 
	(
		SELECT count(*) as tong
		FROM c12_quanlydetai.giaovien
		group by MABM
    );
    
-- cách 2
SELECT MABM
FROM c12_quanlydetai.giaovien
group by MABM
having count(*) >=  all 
	(
		SELECT count(*) as tong
		FROM c12_quanlydetai.giaovien
		group by MABM
    );
-- Câu A12: Cho biết các giáo viên không có người thân
select *
from giaovien as gv
where not exists (select * from nguoithan as nt where gv.magv = nt.magv);

-- Câu A12 dùng not in
select *
from giaovien as gv
where gv.magv not in (select nt.magv from nguoithan as nt where gv.magv = nt.magv);

-- Câu A12 dùng left join
select *
from giaovien as gv left join nguoithan as nt on gv.magv = nt.magv
where nt.magv is null;

-- Câu A20: Cho biết những tên bộ môn, họ tên của trưởng bộ môn và số lượng giáo viên của
-- bộ môn có đông giáo viên nhất
SELECT gv.MABM, bm.TENBM, gv.HOTEN,count(*) as soluonggiaovien
FROM c12_quanlydetai.giaovien as gv join bomon as bm on bm.MABM = gv.MABM
group by MABM
having soluonggiaovien >=  all 
	(
		SELECT count(*) as tong
		FROM c12_quanlydetai.giaovien
		group by MABM
    );
    
-- Câu B3: Tìm các giáo viên vừa tham gia đề tài vừa là trưởng bộ môn.
select *
from bomon as bm join thamgiadt tgdt on bm.TRUONGBM = tgdt.MAGV;

-- Cho biết tên những đề tài mà giáo viên Nguyễn Hoài An id =001 chưa tham gia
-- Cách sai
select * 
from giaovien as gv join thamgiadt as tgdt on gv.MAGV = tgdt.MAGV
right join detai as dt on dt.MADT = tgdt.MADT
where gv.MAGV = '001';
-- Sửa lai cách BỊ SAI ở trên
select *
from (
	select gv.MAGV, tgdt.MADT
	from giaovien as gv join thamgiadt as tgdt on gv.MAGV = tgdt.MAGV
	where gv.MAGV = '001'
	) as temp right join detai as dt on temp.MADT = dt.MADT
where temp.MADT is null;

-- Sửa lai cách theo cách 2
select * 
from detai as dt
where dt.MADT not in 
	(
		select MADT
        from thamgiadt as tgdt
        where tgdt.MAGV = '001'
	);






