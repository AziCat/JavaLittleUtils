prompt PL/SQL Developer import file
prompt Created on 2017年9月13日 by yanjunhao
set feedback off
set define off
prompt Disabling triggers for DICTIONARYKIND...
alter table DICTIONARYKIND disable all triggers;
prompt Deleting DICTIONARYKIND...
delete from DICTIONARYKIND where kind='MC_TYPE';
commit;
prompt Loading DICTIONARYKIND...
insert into DICTIONARYKIND (kind, superkind, detail, source, mask, note, dictsize, frequencyinuse, lastupdatedby, lastupdatedtime, refreshtime, downloadflag, reservation01, reservation02, dictionarykindid, systemmask, bigdict, fbzt)
values ('MC_TYPE', null, '消息中心消息类型', '自定义', null, null, null, null, null, null, null, null, null, null, 'MC_TYPE', null, null, null);
commit;
prompt 1 records loaded
prompt Enabling triggers for DICTIONARYKIND...
alter table DICTIONARYKIND enable all triggers;
set feedback on
set define on
prompt Done.
