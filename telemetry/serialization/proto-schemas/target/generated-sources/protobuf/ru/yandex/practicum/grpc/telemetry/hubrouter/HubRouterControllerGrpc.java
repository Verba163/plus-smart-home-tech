package ru.yandex.practicum.grpc.telemetry.hubrouter;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: telemetry/sesrvices/hub_router.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HubRouterControllerGrpc {

  private HubRouterControllerGrpc() {}

  public static final java.lang.String SERVICE_NAME = "telemetry.service.hubrouter.HubRouterController";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest,
      com.google.protobuf.Empty> getHandleDeviceActionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "handleDeviceAction",
      requestType = ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest,
      com.google.protobuf.Empty> getHandleDeviceActionMethod() {
    io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest, com.google.protobuf.Empty> getHandleDeviceActionMethod;
    if ((getHandleDeviceActionMethod = HubRouterControllerGrpc.getHandleDeviceActionMethod) == null) {
      synchronized (HubRouterControllerGrpc.class) {
        if ((getHandleDeviceActionMethod = HubRouterControllerGrpc.getHandleDeviceActionMethod) == null) {
          HubRouterControllerGrpc.getHandleDeviceActionMethod = getHandleDeviceActionMethod =
              io.grpc.MethodDescriptor.<ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "handleDeviceAction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new HubRouterControllerMethodDescriptorSupplier("handleDeviceAction"))
              .build();
        }
      }
    }
    return getHandleDeviceActionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HubRouterControllerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerStub>() {
        @java.lang.Override
        public HubRouterControllerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HubRouterControllerStub(channel, callOptions);
        }
      };
    return HubRouterControllerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HubRouterControllerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerBlockingStub>() {
        @java.lang.Override
        public HubRouterControllerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HubRouterControllerBlockingStub(channel, callOptions);
        }
      };
    return HubRouterControllerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HubRouterControllerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HubRouterControllerFutureStub>() {
        @java.lang.Override
        public HubRouterControllerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HubRouterControllerFutureStub(channel, callOptions);
        }
      };
    return HubRouterControllerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void handleDeviceAction(ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleDeviceActionMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HubRouterController.
   */
  public static abstract class HubRouterControllerImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HubRouterControllerGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HubRouterController.
   */
  public static final class HubRouterControllerStub
      extends io.grpc.stub.AbstractAsyncStub<HubRouterControllerStub> {
    private HubRouterControllerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HubRouterControllerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HubRouterControllerStub(channel, callOptions);
    }

    /**
     */
    public void handleDeviceAction(ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleDeviceActionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HubRouterController.
   */
  public static final class HubRouterControllerBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HubRouterControllerBlockingStub> {
    private HubRouterControllerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HubRouterControllerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HubRouterControllerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty handleDeviceAction(ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleDeviceActionMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HubRouterController.
   */
  public static final class HubRouterControllerFutureStub
      extends io.grpc.stub.AbstractFutureStub<HubRouterControllerFutureStub> {
    private HubRouterControllerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HubRouterControllerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HubRouterControllerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handleDeviceAction(
        ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleDeviceActionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HANDLE_DEVICE_ACTION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDLE_DEVICE_ACTION:
          serviceImpl.handleDeviceAction((ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getHandleDeviceActionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest,
              com.google.protobuf.Empty>(
                service, METHODID_HANDLE_DEVICE_ACTION)))
        .build();
  }

  private static abstract class HubRouterControllerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HubRouterControllerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouter.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HubRouterController");
    }
  }

  private static final class HubRouterControllerFileDescriptorSupplier
      extends HubRouterControllerBaseDescriptorSupplier {
    HubRouterControllerFileDescriptorSupplier() {}
  }

  private static final class HubRouterControllerMethodDescriptorSupplier
      extends HubRouterControllerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HubRouterControllerMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HubRouterControllerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HubRouterControllerFileDescriptorSupplier())
              .addMethod(getHandleDeviceActionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
