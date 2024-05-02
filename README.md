# GeminiBI
A BI platform built on top of Google Gemini 
The goal to help me not familiar not familiar with BI to get benefit from Gemini and become a data scientist expert!

![GenimiBI-home 1.png](https://raw.githubusercontent.com/RayZGit/GeminiBI/main/resources/GenimiBI-home%201.png)

### Functional Requirements
##### Current Scope
1. User should be able to input original data 
2. Service should generate relevant data analysis 
4.  AI to generate description

![](https://raw.githubusercontent.com/RayZGit/GeminiBI/main/resources/GenimiBI-Current.png)
**BI Business Logic**
1. User inputs his value
	1. The goal
	2. Original Data
	3. Basic Data Analysis
2. Backend Validation
	1. Validate User Input
	2. Authorization
3. Backend AI Platform 
4. Frontend Displaying

##### Future Scope
- Data Management
-  Async Data Generalization 


![GenimiBI-Future.png](https://raw.githubusercontent.com/RayZGit/GeminiBI/main/resources/GenimiBI-Future.png)
#### Tech Stack
- **Front-End**:  React, Apache Echarts
- **Back-End**: Java, Springboot,  MySQL,  Gemini
- **Tools** EasyExcel, Mybatis Plus, Swagger, Knef4j


### Database
```sql
-- user --
create table if not exists user  
(  
    id           bigint auto_increment comment 'id' primary key,  
    userAccount  varchar(256)                   not null comment 'account',  
    userPassword varchar(512)                   not null comment 'password',  
    userName     varchar(256)                   null comment 'user name',  
    userAvatar   varchar(1024)                  null comment 'user avatar',  
    userProfile  varchar(512)                   null comment 'user profile',  
    userRole     varchar(256) default 'user'  not null comment 'user role：user/admin/ban',  
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'creation time',  
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',  
    isDelete     tinyint      default 0          not null comment 'deleted',  
    index idx_userAccount (userAccount)  
) comment 'user' collate = utf8mb4_unicode_ci;

--- chart ---

create table if not exists chart  
(  
    id         bigint auto_increment comment 'id' primary key,  
    goal       text                               null comment 'analysis goal',  
    chartData  text                               null comment 'table analysis',  
    chartType  varchar(128)                       null comment 'chart type',  
    genChart   text                               null comment 'generative chart',  
    genResult  text                               null comment 'generative description',  
    createTime datetime default CURRENT_TIMESTAMP not null comment 'create time',  
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',  
    isDelete   tinyint  default 0                 not null comment 'deleted'  
) comment 'chart' collate = utf8mb4_unicode_ci;
```

