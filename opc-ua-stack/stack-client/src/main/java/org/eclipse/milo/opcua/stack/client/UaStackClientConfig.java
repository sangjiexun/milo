/*
 * Copyright (c) 2018 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.stack.client;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.HashedWheelTimer;
import org.eclipse.milo.opcua.stack.core.application.CertificateValidator;
import org.eclipse.milo.opcua.stack.core.channel.ChannelConfig;
import org.eclipse.milo.opcua.stack.core.serialization.EncodingLimits;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

public interface UaStackClientConfig {

    EndpointDescription getEndpoint();

    /**
     * Get the {@link KeyPair} to use.
     * <p>
     * May be absent if connecting without security, must be present if connecting with security.
     *
     * @return an {@link Optional} containing the {@link KeyPair} to use.
     */
    Optional<KeyPair> getKeyPair();

    /**
     * Get the {@link X509Certificate} to use.
     * <p>
     * May be absent if connecting without security, must be present if connecting with security.
     *
     * @return an {@link Optional} containing the {@link X509Certificate} to use.
     */
    Optional<X509Certificate> getCertificate();

    /**
     * Get the {@link X509Certificate} to use as well as any certificates in the certificate chain.
     *
     * @return the {@link X509Certificate} to use as well as any certificates in the certificate chain.
     */
    Optional<X509Certificate[]> getCertificateChain();

    /**
     * Get the {@link CertificateValidator} this client will use to validate server certificates when connecting.
     *
     * @return the {@link CertificateValidator} this client will use to validate server certificates when connecting.
     */
    CertificateValidator getCertificateValidator();

    /**
     * @return the configured {@link EncodingLimits}.
     */
    EncodingLimits getEncodingLimits();

    /**
     * @return the {@link ExecutorService} the transport will use.
     */
    ExecutorService getExecutor();

    /**
     * @return the {@link NioEventLoopGroup} the transport will use.
     */
    NioEventLoopGroup getEventLoop();

    /**
     * @return the {@link HashedWheelTimer} the transport will use.
     */
    HashedWheelTimer getWheelTimer();

    /**
     * @return the timeout, in milliseconds, when opening a socket connection to a remote host.
     */
    UInteger getConnectTimeout();

    /**
     * @return the timeout, in milliseconds, to wait for an Acknowledge message in response to the client's
     * Hello message.
     */
    UInteger getAcknowledgeTimeout();

    /**
     * @return the timeout, in milliseconds, before failing a request due to timeout.
     */
    UInteger getRequestTimeout();

    /**
     * @return the {@link ChannelConfig} to use when creating secure channels.
     */
    ChannelConfig getChannelConfig();

    /**
     * @return the secure channel lifetime to request, in milliseconds.
     */
    UInteger getChannelLifetime();

    static UaStackClientConfigBuilder builder() {
        return new UaStackClientConfigBuilder();
    }

    /**
     * Copy the values from an existing {@link UaStackClientConfig} into a new {@link UaStackClientConfigBuilder}.
     * This builder can be used to make any desired modifications before invoking
     * {@link UaStackClientConfigBuilder#build()} to produce a new config.
     *
     * @param config the {@link UaStackClientConfig} to copy from.
     * @return a {@link UaStackClientConfigBuilder} pre-populated with values from {@code config}.
     */
    static UaStackClientConfigBuilder copy(UaStackClientConfig config) {
        UaStackClientConfigBuilder builder = new UaStackClientConfigBuilder();

        builder.setEndpoint(config.getEndpoint());
        config.getKeyPair().ifPresent(builder::setKeyPair);
        config.getCertificate().ifPresent(builder::setCertificate);
        config.getCertificateChain().ifPresent(builder::setCertificateChain);
        builder.setCertificateValidator(config.getCertificateValidator());
        builder.setChannelConfig(config.getChannelConfig());
        builder.setChannelLifetime(config.getChannelLifetime());
        builder.setExecutor(config.getExecutor());
        builder.setEventLoop(config.getEventLoop());
        builder.setWheelTimer(config.getWheelTimer());
        builder.setConnectTimeout(config.getConnectTimeout());
        builder.setAcknowledgeTimeout(config.getAcknowledgeTimeout());
        builder.setRequestTimeout(config.getRequestTimeout());

        return builder;
    }

    /**
     * Copy the values from an existing {@link UaStackClientConfig} into a new {@link UaStackClientConfigBuilder}
     * and then submit the builder to the provided consumer for modification.
     *
     * @param config   the {@link UaStackClientConfig} to copy from.
     * @param consumer a {@link Consumer} that may modify the builder.
     * @return a {@link UaStackClientConfig} built from the builder provided to {@code consumer}.
     */
    static UaStackClientConfig copy(
        UaStackClientConfig config,
        Consumer<UaStackClientConfigBuilder> consumer) {

        UaStackClientConfigBuilder builder = copy(config);

        consumer.accept(builder);

        return builder.build();
    }

}
