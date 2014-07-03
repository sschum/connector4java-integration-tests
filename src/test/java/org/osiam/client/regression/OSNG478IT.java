package org.osiam.client.regression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.osiam.client.AbstractIntegrationTestBase;
import org.osiam.client.query.Query;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.resources.scim.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseTearDown(value = "/database_tear_down.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/database_seeds/OSNG478IT/user_without_name.xml")
public class OSNG478IT extends AbstractIntegrationTestBase {

    @Test
    public void search_and_sort(){
        Query query = new QueryBuilder()
                        .ascending("name.givenName")
                        .build();
        
        
        SCIMSearchResult<User> result = this.oConnector.searchUsers(query, accessToken);
        
        assertEquals(2, result.getTotalResults());
        assertEquals(result.getTotalResults(), result.getResources().size());
    }
}
