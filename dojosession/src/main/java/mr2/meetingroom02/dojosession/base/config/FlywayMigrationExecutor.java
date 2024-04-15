package mr2.meetingroom02.dojosession.base.config;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.sql.DataSource;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class FlywayMigrationExecutor {
    @Resource(lookup = "java:/DojoSessionDS")
    DataSource dataSource;

    @PostConstruct
    public void migrate(){
       Flyway flyway = Flyway.configure()
               .dataSource(dataSource)
               .schemas("public")
               .baselineOnMigrate(true)
               .load();
       flyway.migrate();
    }
}
