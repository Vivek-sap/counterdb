--
-- creating a test invocation chain with the dummy-adapter
--


insert into wcd_process(name, description) values('COMA_DEV:ONE_STEP', 'One-Step-Test for WCD development');

select @process_id:=id from wcd_process where wcd_process.name = 'COMA_DEV:ONE_STEP';

insert into wcd_step(name, adapter, action, PROCESS_ID, description)
values(
    'COMA_DEV:ONE_STEP:TEST_STEP_01',
    'dummy-adapter',
    'mock01',
    @process_id,
    'A mock step');

update wcd_process 
set first_step_id = (select id from wcd_step where name = 'COMA_DEV:ONE_STEP:TEST_STEP_01') 
where id = @process_id;


insert into wcd_invo_chain(name, footprint, process_id, is_idempotent)
values('ONE_STEP', 'VKD', @process_id, true);

insert into wcd_invo_chain(name, footprint, process_id, is_idempotent)
values('ONE_STEP', 'UM', @process_id, true);
