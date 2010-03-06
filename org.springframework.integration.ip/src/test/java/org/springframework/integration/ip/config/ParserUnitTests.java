/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.ip.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.ip.tcp.CustomNetSocketReader;
import org.springframework.integration.ip.tcp.CustomNetSocketWriter;
import org.springframework.integration.ip.tcp.CustomNioSocketReader;
import org.springframework.integration.ip.tcp.CustomNioSocketWriter;
import org.springframework.integration.ip.tcp.MessageFormats;
import org.springframework.integration.ip.tcp.TcpNetReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpNetSendingMessageHandler;
import org.springframework.integration.ip.tcp.TcpNioReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpNioSendingMessageHandler;
import org.springframework.integration.ip.udp.DatagramPacketMessageMapper;
import org.springframework.integration.ip.udp.MulticastReceivingChannelAdapter;
import org.springframework.integration.ip.udp.MulticastSendingMessageHandler;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Gary Russell
 *
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ParserUnitTests {

	@Autowired
	@Qualifier(value="testInUdp")
	UnicastReceivingChannelAdapter udpIn;

	@Autowired
	@Qualifier(value="testInUdpMulticast")
	MulticastReceivingChannelAdapter udpInMulticast;

	@Autowired
	@Qualifier(value="testInTcpNio")
	TcpNioReceivingChannelAdapter tcpInNio;
	
	@Autowired
	@Qualifier(value="testInTcpNioDirect")
	TcpNioReceivingChannelAdapter tcpInNioDirect;
	
	@Autowired
	@Qualifier(value="testInTcpNet")
	TcpNetReceivingChannelAdapter tcpInNet;
	
	@Autowired
	@Qualifier(value="org.springframework.integration.ip.udp.UnicastSendingMessageHandler#0")
	UnicastSendingMessageHandler udpOut;

	@Autowired
	@Qualifier(value="org.springframework.integration.ip.udp.MulticastSendingMessageHandler#0")
	MulticastSendingMessageHandler udpOutMulticast;

	@Autowired
	@Qualifier(value="org.springframework.integration.ip.tcp.TcpNioSendingMessageHandler#0")
	TcpNioSendingMessageHandler tcpOutNio;

	@Autowired
	@Qualifier(value="org.springframework.integration.ip.tcp.TcpNioSendingMessageHandler#1")
	TcpNioSendingMessageHandler tcpOutNioDirect;

	@Autowired
	@Qualifier(value="org.springframework.integration.ip.tcp.TcpNetSendingMessageHandler#0")
	TcpNetSendingMessageHandler tcpOutNet;

	
	@Test
	public void testInUdp() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(udpIn);
		assertTrue(udpIn.getPort() >= 5000);
		assertEquals(27, dfa.getPropertyValue("poolSize"));
		assertEquals(29, dfa.getPropertyValue("receiveBufferSize"));
		assertEquals(30, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(31, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(32, dfa.getPropertyValue("soTimeout"));
	}
	
	@Test
	public void testInUdpMulticast() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(udpInMulticast);
		assertTrue(udpInMulticast.getPort() >= 5100);
		assertEquals("225.6.7.8", dfa.getPropertyValue("group"));
		assertEquals(27, dfa.getPropertyValue("poolSize"));
		assertEquals(29, dfa.getPropertyValue("receiveBufferSize"));
		assertEquals(30, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(31, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(32, dfa.getPropertyValue("soTimeout"));
	}
	
	@Test
	public void testInTcpNio() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpInNio);
		assertTrue(tcpInNio.getPort() >= 5200);
		assertEquals(CustomNioSocketReader.class, dfa.getPropertyValue("customSocketReader"));
		assertEquals(false, dfa.getPropertyValue("usingDirectBuffers"));
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(27, dfa.getPropertyValue("poolSize"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(29, dfa.getPropertyValue("receiveBufferSize"));
		assertEquals(30, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(32, dfa.getPropertyValue("soTimeout"));
	}
	
	@Test
	public void testInTcpNioDirect() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpInNioDirect);
		assertTrue(tcpInNioDirect.getPort() >= 5300);
		assertEquals(CustomNioSocketReader.class, dfa.getPropertyValue("customSocketReader"));
		assertEquals(true, dfa.getPropertyValue("usingDirectBuffers"));
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(27, dfa.getPropertyValue("poolSize"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(29, dfa.getPropertyValue("receiveBufferSize"));
		assertEquals(30, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(32, dfa.getPropertyValue("soTimeout"));
	}

	@Test
	public void testInTcpNet() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpInNet);
		assertTrue(tcpInNet.getPort() >= 5400);
		assertEquals(CustomNetSocketReader.class, dfa.getPropertyValue("customSocketReader"));
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(27, dfa.getPropertyValue("poolSize"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(29, dfa.getPropertyValue("receiveBufferSize"));
		assertEquals(30, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(32, dfa.getPropertyValue("soTimeout"));
	}
	
	@Test
	public void testOutUdp() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(udpOut);
		assertTrue(udpOut.getPort() >= 6000);
		assertEquals("localhost", dfa.getPropertyValue("host"));
		int ackPort = (Integer) dfa.getPropertyValue("ackPort");
		assertTrue("Expected ackPort >= 7000 was:" + ackPort, ackPort >= 7000);
		DatagramPacketMessageMapper mapper = (DatagramPacketMessageMapper) dfa
				.getPropertyValue("mapper");
		String ackAddress = (String) new DirectFieldAccessor(mapper)
				.getPropertyValue("ackAddress");
		assertEquals("somehost:" + ackPort, ackAddress);
		assertEquals(51, dfa.getPropertyValue("ackTimeout"));
		assertEquals(true, dfa.getPropertyValue("waitForAck"));
		assertEquals(52, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(53, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(54, dfa.getPropertyValue("soTimeout"));
	}
	
	@Test
	public void testOutUdpMulticast() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(udpOutMulticast);
		assertTrue(udpOutMulticast.getPort() >= 6100);
		assertEquals("225.6.7.8", dfa.getPropertyValue("host"));
		int ackPort = (Integer) dfa.getPropertyValue("ackPort");
		assertTrue("Expected ackPort >= 7100 was:" + ackPort, ackPort >= 7100);
		DatagramPacketMessageMapper mapper = (DatagramPacketMessageMapper) dfa
				.getPropertyValue("mapper");
		String ackAddress = (String) new DirectFieldAccessor(mapper)
				.getPropertyValue("ackAddress");
		assertEquals("somehost:" + ackPort, ackAddress);
		assertEquals(51, dfa.getPropertyValue("ackTimeout"));
		assertEquals(true, dfa.getPropertyValue("waitForAck"));
		assertEquals(52, dfa.getPropertyValue("soReceiveBufferSize"));
		assertEquals(53, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(54, dfa.getPropertyValue("soTimeout"));
		assertEquals(55, dfa.getPropertyValue("timeToLive"));
	}

	@Test
	public void testOutTcpNio() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpOutNio);
		assertTrue(tcpOutNio.getPort() >= 6200);
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(CustomNioSocketWriter.class, dfa.getPropertyValue("customSocketWriter"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(3, dfa.getPropertyValue("soLinger"));
		assertEquals(true, dfa.getPropertyValue("soTcpNoDelay"));
		assertEquals(27, dfa.getPropertyValue("soTrafficClass"));
		assertEquals(53, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(54, dfa.getPropertyValue("soTimeout"));
		assertEquals(false, dfa.getPropertyValue("usingDirectBuffers"));
		
	}

	@Test
	public void testOutTcpNioDirect() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpOutNioDirect);
		assertTrue(tcpOutNioDirect.getPort() >= 6300);
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(CustomNioSocketWriter.class, dfa.getPropertyValue("customSocketWriter"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(3, dfa.getPropertyValue("soLinger"));
		assertEquals(true, dfa.getPropertyValue("soTcpNoDelay"));
		assertEquals(27, dfa.getPropertyValue("soTrafficClass"));
		assertEquals(53, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(54, dfa.getPropertyValue("soTimeout"));
		assertEquals(true, dfa.getPropertyValue("usingDirectBuffers"));
		
	}

	@Test
	public void testOutTcpNet() {
		DirectFieldAccessor dfa = new DirectFieldAccessor(tcpOutNet);
		assertTrue(tcpOutNet.getPort() >= 6400);
		assertEquals(MessageFormats.FORMAT_STX_ETX, dfa.getPropertyValue("messageFormat"));
		assertEquals(CustomNetSocketWriter.class, dfa.getPropertyValue("customSocketWriter"));
		assertEquals(true, dfa.getPropertyValue("soKeepAlive"));
		assertEquals(3, dfa.getPropertyValue("soLinger"));
		assertEquals(true, dfa.getPropertyValue("soTcpNoDelay"));
		assertEquals(27, dfa.getPropertyValue("soTrafficClass"));
		assertEquals(53, dfa.getPropertyValue("soSendBufferSize"));
		assertEquals(54, dfa.getPropertyValue("soTimeout"));
		
	}
}
