----
---- creating a test invocation chain with the dummy-adapter
----

insert into wcd_process(name, description) values('COMA_DEV:TWO_STEPS', 'Two-Step-Test chain for WCD development');

select @process_id:=id from wcd_process where wcd_process.name = 'COMA_DEV:TWO_STEPS';

insert into wcd_step(name, adapter, action, PROCESS_ID, description)
values(
    'COMA_DEV:TWO_STEPS:TEST_STEP_01',
    'dummy-adapter',
    'mock01',
    @process_id,
    'The first step');
    
insert into wcd_step(name, adapter, action, PROCESS_ID, description)
values(
    'COMA_DEV:TWO_STEPS:TEST_STEP_02',
    'second-dummy-adapter',
    'mock02',
    @process_id,
    'A second step');


update wcd_process 
set first_step_id = (select id from wcd_step where name = 'COMA_DEV:TWO_STEPS:TEST_STEP_01') 
where id = @process_id;

insert into wcd_transition(name, step_before_id, step_after_id) values('COMA_DEV:TWO_STEPS:TRANS_01', 
    (select id from wcd_step where wcd_step.name = 'COMA_DEV:TWO_STEPS:TEST_STEP_01'), 
    (select id from wcd_step where wcd_step.name = 'COMA_DEV:TWO_STEPS:TEST_STEP_02'));

insert into wcd_invo_chain(name, footprint, process_id, is_idempotent)
values('TWO_STEPS', 'VKD', @process_id, true);

insert into wcd_invo_chain(name, footprint, process_id, is_idempotent)
values('TWO_STEPS', 'UM', @process_id, true);


--insert into wcd_step(name, adapter, action, PROCESS_ID, description)
--values(
--    'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_01',
--    'dummy-adapter',
--    'mock01',
--    (select id from wcd_process where wcd_process.name = 'COMA_DEV:TEST01'),
--    'A step leading to a decision');

--update wcd_process set first_step_id = (select id from wcd_step where name = 'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_01') where (name = 'COMA_DEV:TEST_NEXT_01');

--insert into wcd_step(name, adapter, action, PROCESS_ID, description)
--values(
--    'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_02',
--    'decision-adapter',
--    'dec01',
--    (select id from wcd_process where wcd_process.name = 'COMA_DEV:TEST_NEXT_01'),
--    'A decision step');


--insert into wcd_transition(name, step_before_id, step_after_id) values('COMA_DEV:TEST_NEXT_01:TEST_TRANS_01',
--    (select id from wcd_step where wcd_step.name = 'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_01'),
--    (select id from wcd_step where wcd_step.name = 'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_02'));

--insert into wcd_invo_chain(name, footprint, process_id)
--values (
--    'COMA_INVO_NEXT01',
--    'VKD',
--    (select id from wcd_process where wcd_process.name = 'COMA_DEV:TEST_NEXT_01'));

--insert into wcd_invo_chain(name, footprint, process_id)
--values (
--    'COMA_INVO_NEXT01',
--    'UM',
--    (select id from wcd_process where wcd_process.name = 'COMA_DEV:TEST_NEXT_01'));

--insert into wcd_decision(name, step_id, x_path_expression, next_invocation_chain_id, footprint) values (
--    'COMA_DEV:CONFIRM_IMMEDIATE',
--    (select s.id from wcd_step s where s.name = 'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_02' and s.adapter = 'decision-adapter'),
--    '/cns:coma/cns:wholesaleCustomer[cns:email=''ralf.siedow@web.de'']',
--    (select id from wcd_invo_chain ic where ic.name = 'COMA_INVO_ONE_STEP01' and ic.footprint = 'VKD'),
--    'VKD');

--insert into wcd_decision(name, step_id, x_path_expression, next_invocation_chain_id, footprint) values (
--    'COMA_DEV:CONFIRM_IMMEDIATE_UM',
--    (select s.id from wcd_step s where s.name = 'COMA_DEV:TEST_NEXT_01:TEST_STEP_OPM_02' and s.adapter = 'decision-adapter'),
--    '/cns:coma/cns:wholesaleCustomer[cns:email=''ralf.siedow@web.de'']',
--    (select id from wcd_invo_chain ic where ic.name = 'COMA_INVO_ONE_STEP01' and ic.footprint = 'UM'),
--    'UM');

