-- 接口表
create table if not exists my_db.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '接口名称',
    `description` varchar(256) not null comment '用户名',
    `url` text not null comment '请求类型',
    `requestParams` text null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `accessKey` varchar(512) null comment 'ak',
    `secertKey` varchar(512) null comment 'sk',
    `status` int default 0 not null comment '接口状态0是默认，1是开启',
    `method` varchar(256) not null comment '用户名',
    `userId` bigint not null comment '用户名',
    `createTime` datetime default 'CURRENT_TIMESTAMP' not null comment '创建时间',
    `updateTime` datetime default 'CURRENT_TIMESTAMP' not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口表';

insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('任致远', '董煜祺', 'www.jermaine-cartwright.io', '姚峻熙', '汪楷瑞', 0, '顾健柏', 176);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('洪天宇', '雷哲瀚', 'www.eleonora-rath.io', '程航', '陆瑞霖', 0, '戴弘文', 77330699);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('江远航', '张浩', 'www.danilo-stiedemann.org', '江子骞', '卢弘文', 0, '冯鑫鹏', 5947939544);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('阎浩然', '马越彬', 'www.owen-oreilly.io', '段晟睿', '夏博涛', 0, '刘凯瑞', 9739);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('贾哲瀚', '蒋健雄', 'www.chelsea-runolfsson.co', '贺君浩', '汪擎苍', 0, '吴烨霖', 452214);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邹振家', '邱熠彤', 'www.jamaal-leuschke.name', '夏思聪', '石明杰', 0, '罗正豪', 806);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('何伟宸', '唐绍齐', 'www.jeremy-schmeler.co', '毛君浩', '贺弘文', 0, '朱弘文', 33568346);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('于哲瀚', '孔擎宇', 'www.norma-durgan.co', '江明辉', '方昊强', 0, '金聪健', 35107752);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('万擎苍', '邱荣轩', 'www.milton-spinka.org', '宋思淼', '覃昊强', 0, '贺鹏飞', 8148534914);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('曾睿渊', '谭志强', 'www.cythia-auer.co', '邹懿轩', '段子轩', 0, '范博超', 62);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('钟越泽', '白明杰', 'www.rafael-johnston.io', '杜伟泽', '廖思', 0, '崔子涵', 46);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('沈楷瑞', '邵涛', 'www.barbie-kunde.net', '曾子涵', '尹烨霖', 0, '顾擎苍', 3);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('石炫明', '阎远航', 'www.katlyn-swaniawski.net', '熊潇然', '雷烨霖', 0, '龚风华', 8803652896);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('周绍辉', '李展鹏', 'www.travis-wiegand.com', '蒋绍辉', '苏钰轩', 0, '马明', 91064139);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('万浩然', '孔浩轩', 'www.usha-brekke.com', '吴远航', '于瑾瑜', 0, '钱哲瀚', 539045627);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('石晟睿', '陈晟睿', 'www.elden-kulas.net', '史俊驰', '孟晟睿', 0, '龚熠彤', 1559);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('龚睿渊', '魏天翊', 'www.gerard-schumm.biz', '戴鸿煊', '傅弘文', 0, '赵明轩', 9);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('冯博涛', '杜文博', 'www.hellen-kris.io', '尹航', '程志泽', 0, '崔天磊', 800);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邵浩然', '黄明轩', 'www.jackie-swift.com', '唐梓晨', '徐浩然', 0, '武健雄', 2138053445);
insert into my_db.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吕荣轩', '蔡智渊', 'www.malvina-kuvalis.net', '姚弘文', '袁展鹏', 0, '刘健柏', 4941643855);