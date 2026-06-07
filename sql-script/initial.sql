-- 默認使用postgres庫
create database demo;

connect demo;

CREATE TABLE mr_user
(
id bigserial primary key,
username varchar(256) not null,								-- 員工用戶名
password varchar(1024) not null,							-- 密碼，加密過後
email varchar(256),											-- 郵箱
user_code varchar(128) not null, 							-- 員工編號
real_name varchar(256) not null, 							-- 真實姓名
mobile varchar(32) not null,								-- 手機號碼
enable boolean not null default true,	 					-- 用戶是否可用
audit boolean not null default false,						-- 是否是審核員
dept_id int not null default -1,							-- 所屬部門id
create_time timestamp default current_timestamp,           	-- 創建時間
update_time timestamp            							-- 更新時間
);

create unique index uk_username on mr_user(username);



CREATE TABLE mr_dept
(
id serial primary key,										-- 部門id
parent_id int not null default -1,							-- 父部門id
dept_name varchar(64) not null,								-- 部門名稱
enable boolean default true,								-- 是否可用
creator_id int not null default 1,							-- 創建人id
create_time timestamp not null default current_timestamp,	-- 創建時間戳
updater_id int,												-- 更新人id
update_time timestamp										-- 更新時間
);


CREATE TABLE mr_room
(
id serial primary key,
room_name varchar(256) not null,
capacity smallint not null,
enable boolean default true,
creator_id int not null,
create_time timestamp not null default current_timestamp,
updater_id int ,
update_time timestamp
);


create unique index uk_name on mr_room(room_name);




CREATE TABLE mr_reservation
(
id bigserial primary key,
code varchar(128),												-- 預約編號
start_time timestamp not null,									-- 預約開始時間
end_time timestamp not null,									-- 結束時間
room_id int not null,											-- 會議室id
user_count smallint not null,  									-- 參會人數
status varchar(32) not null default 'processing',				-- 預約狀態：processing，rejected，approved
title varchar(64) not null,										-- 會議主旨
user_id int not null,											-- 預約人id
dept_id int not null,											-- 部門id
order_factory varchar(128),										-- 預約場商
reject_reason varchar(128),										-- 退回理由
remark varchar(1024),											-- 備注
creator_id int not null,										-- 創建人id
create_time timestamp not null default current_timestamp,		-- 創建時間戳
updater_id int,													-- 更新人id
update_time timestamp											-- 更新時間
);


create index idx_roomId on mr_reservation(room_id);
create index idx_roomId_status on mr_reservation(room_id, status);
create index idx_roomId_startEndTime on mr_reservation(room_id, start_time, end_time);



insert into mr_user(username, password, user_code, real_name, mobile, dept_id)
values
('admin', 'jldjafjsalkdjlajfdas', '10001000', '大總管', '#0987654321', 1 ),
('qweqw', 'jldjafjsalkdjlajfdds', '10001001', '張開心', '#0987654320', 2 ),
('qweq4', 'jldjafjsalkdjlajfdds', '10001002', '王開心', '#0987654322', 2 ),
('qweq5', 'jldjafjsalkdjlajfdds', '10001003', '李開心', '#0987654323', 3 ),
('qweq6', 'jldjafjsalkdjlajfdds', '10001004', '趙開心', '#0987654323', 3 ),
('qweq7', 'jldjafjsalkdjlajfdds', '10001005', '陳開心', '#0987654323', 3 ),
('qweq8', 'jldjafjsalkdjlajfdds', '10001006', '黃開心', '#0987654323', 4 ),
('qweq9', 'jldjafjsalkdjlajfdds', '10001007', '馮開心', '#0987654323', 4 ),
('qweqq', 'jldjafjsalkdjlajfdds', '10001008', '孫開心', '#0987654323', 4 ),
('qweqe', 'jldjafjsalkdjlajfdds', '10001009', '劉開心', '#0987654323', 5 ),
('qweqt', 'jldjafjsalkdjlajfdds', '10001010', '項開心', '#0987654323', 5 );



insert into mr_dept (parent_id, dept_name)
values
(-1, '總裁辦'),
(1, '市場部'),
(1, '研發部'),
(1, '人資部'),
(1, '財務部');




insert into mr_room (room_name, capacity, creator_id)
values
('會議室0301', 15, 1),
('會議室0302', 15, 1),
('會議室0303', 15, 1),
('會議室0304', 15, 1),
('會議室0305', 15, 1),
('會議室0306', 15, 1),
('會議室0307', 15, 1),
('會議室0308', 15, 1),
('會議室0309', 15, 1),
('會議室0401', 15, 1),
('會議室0402', 15, 1),
('會議室0403', 15, 1),
('會議室0404', 15, 1),
('會議室0405', 15, 1),
('會議室0406', 15, 1),
('會議室0501', 15, 1),
('會議室0502', 15, 1),
('會議室0503', 15, 1),
('會議室0504', 15, 1),
('會議室0505', 15, 1),
('會議室0506', 15, 1),
('會議室0507', 15, 1),
('會議室0508', 15, 1),
('會議室0600', 15, 1),
('會議室0601', 15, 1),
('會議室0602', 15, 1),
('會議室0603', 15, 1),
('會議室0604', 15, 1),
('會議室0605', 15, 1),
('會議室0701', 15, 1),
('會議室0702', 15, 1),
('會議室0703', 15, 1),
('會議室0704', 15, 1),
('會議室0705', 15, 1),
('會議室0706', 15, 1),
('會議室0707', 15, 1),
('會議室0708', 15, 1),
('會議室0800', 15, 1),
('會議室0801', 15, 1),
('會議室0802', 15, 1),
('會議室0803', 15, 1),
('會議室0804', 15, 1),
('會議室0805', 15, 1),
('會議室0901', 15, 1),
('會議室0902', 15, 1),
('會議室0903', 15, 1),
('會議室0904', 15, 1),
('會議室0905', 15, 1),
('會議室0906', 15, 1),
('會議室0907', 15, 1),
('會議室0908', 15, 1);



insert into mr_reservation
(start_time, end_time, room_id, user_count, title, user_id, dept_id, order_factory, creator_id)
values
('2026-01-01 10:30:39', '2026-01-01 14:30:39', 3, 5, 'test', 3, 1, '', 3),
('2026-01-01 9:30:39', '2026-01-01 10:30:00', 4, 5, 'test', 3, 1, '', 3),
('2026-01-01 15:30:00', '2026-01-01 16:30:00', 4, 5, 'test', 3, 1, '', 3);
