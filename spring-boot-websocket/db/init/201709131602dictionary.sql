prompt PL/SQL Developer import file
prompt Created on 2017年9月13日 by yanjunhao
set feedback off
set define off
prompt Disabling triggers for DICTIONARY...
alter table DICTIONARY disable all triggers;
prompt Deleting DICTIONARY...
delete from DICTIONARY where kind='MC_TYPE';
commit;
prompt Loading DICTIONARY...
insert into DICTIONARY (dictionaryid, kind, code, supercode, detail, spell, homophony, frequencyinuse, wb, note, lastupdatedby, lastupdatedtime, refreshtime, downloadflag, reservation01, reservation02, reservation03, reservation04, reservation06, reservation07, reservation08, reservation09, reservation05, reservation010, deleteflag)
values ('MC_TYPE_01', 'MC_TYPE', '01', null, '工作提醒', 'GZTX', null, null, null, null, null, null, to_date('11-09-2017 15:54:11', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null);
insert into DICTIONARY (dictionaryid, kind, code, supercode, detail, spell, homophony, frequencyinuse, wb, note, lastupdatedby, lastupdatedtime, refreshtime, downloadflag, reservation01, reservation02, reservation03, reservation04, reservation06, reservation07, reservation08, reservation09, reservation05, reservation010, deleteflag)
values ('MC_TYPE_02', 'MC_TYPE', '02', null, '待办任务', 'DBRW', null, null, null, null, null, null, to_date('11-09-2017 15:54:11', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, null, null, null, null);
commit;
prompt 2 records loaded
prompt Enabling triggers for DICTIONARY...
alter table DICTIONARY enable all triggers;
set feedback on
set define on
prompt Done.
