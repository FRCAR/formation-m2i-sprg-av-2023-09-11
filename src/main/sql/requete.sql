--Récupère un bilan des jobs passés
SELECT BATCH_JOB_INSTANCE.JOB_INSTANCE_ID, JOB_NAME, BATCH_JOB_EXECUTION.JOB_EXECUTION_ID, START_TIME, END_TIME, STATUS, EXIT_CODE, PARAMETER_NAME, PARAMETER_VALUE, IDENTIFYING
FROM BATCH_JOB_INSTANCE 
LEFT OUTER JOIN BATCH_JOB_EXECUTION ON BATCH_JOB_INSTANCE.JOB_INSTANCE_ID = BATCH_JOB_EXECUTION.JOB_INSTANCE_ID
LEFT OUTER JOIN BATCH_JOB_EXECUTION_PARAMS ON BATCH_JOB_EXECUTION.JOB_EXECUTION_ID = BATCH_JOB_EXECUTION_PARAMS.JOB_EXECUTION_ID
ORDER BY START_TIME DESC;