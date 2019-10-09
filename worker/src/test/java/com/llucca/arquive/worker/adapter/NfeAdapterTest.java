package com.llucca.arquive.worker.adapter;

import com.llucca.arquive.worker.model.message.nfe.NfeMessage;
import com.llucca.arquive.worker.model.nfe.Nfe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NfeAdapterTest {

    @Autowired
    private NfeAdapter nfeAdapter;

    @Test
    public void testAdapater(){
        NfeMessage nfeMessage = new NfeMessage();
        nfeMessage.setAccessKey("123");
        nfeMessage.setXml("aaaaaaaa<vNF>12.12</vNF>aaaaaaa");
        Nfe nfe = nfeAdapter.convertFromNfeMessage(nfeMessage);

        Assert.assertNotNull(nfe);
        Assert.assertEquals(nfe.getAccessKey(),"123");
        Assert.assertEquals(nfe.getTotalValue(), new Double(12.12));
    }
}
