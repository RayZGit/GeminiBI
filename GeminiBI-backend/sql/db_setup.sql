create database if not exists geminibi_db;

use geminibi_db;

-- user
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'account',
    userPassword varchar(512)                           not null comment 'password',
    userName     varchar(256)                           null comment 'user name',
    userAvatar   varchar(1024)                          null comment 'user avatar',
    userProfile  varchar(512)                           null comment 'user profile',
    userRole     varchar(256) default 'user'            not null comment 'user role：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'creation time',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete     tinyint      default 0                 not null comment 'deleted',
    index idx_userAccount (userAccount)
) comment 'user' collate = utf8mb4_unicode_ci;

-- chart table
create table if not exists chart
(
    id         bigint auto_increment comment 'id' primary key,
    userId     bigint                             null comment 'user id',
    goal       text                               null comment 'analysis goal',
    chartData  text                               null comment 'table analysis',
    chartType  varchar(128)                       null comment 'chart type',
    genChart   text                               null comment 'generative chart',
    genResult  text                               null comment 'generative description',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete   tinyint  default 0                 not null comment 'deleted'
) comment 'chart' collate = utf8mb4_unicode_ci;
