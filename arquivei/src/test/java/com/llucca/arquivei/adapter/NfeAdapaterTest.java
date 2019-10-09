package com.llucca.arquivei.adapter;

import com.llucca.arquivei.model.client.NfeEncoded;
import com.llucca.arquivei.model.message.Message;
import com.llucca.arquivei.model.message.nfe.NfeMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NfeAdapaterTest {

    @Test
    public void testAdapter(){
        NfeAdapter nfeAdapter = new NfeAdapaterImpl();

        String accessKey = "123";
        String xmlEncoded= "eG1sbGxsbA==";
        String xmlDecoded="xmlllll";
        NfeEncoded nfeEncoded = new NfeEncoded(accessKey,xmlEncoded);
        Message<NfeMessage> nfeMessage = nfeAdapter.convertFromNfeEncodedToMessage(nfeEncoded);

        Assert.assertNotNull(nfeMessage);
        Assert.assertTrue(nfeMessage.getBody().getAccessKey().equals(accessKey));
        Assert.assertTrue(nfeMessage.getBody().getXml().equals(xmlDecoded));

    }

}
