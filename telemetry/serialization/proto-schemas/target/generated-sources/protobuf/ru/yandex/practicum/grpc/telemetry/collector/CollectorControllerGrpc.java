package ru.yandex.practicum.grpc.telemetry.collector;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: telemetry/sesrvices/collector_controller.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CollectorControllerGrpc {

  private CollectorControllerGrpc() {}

  public static final java.lang.String SERVICE_NAME = "telemetry.service.collector.CollectorController";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.SensorEventProto,
      com.google.protobuf.Empty> getCollectSensorEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CollectSensorEvent",
      requestType = ru.yandex.practicum.grpc.telemetry.event.SensorEventProto.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.SensorEventProto,
      com.google.protobuf.Empty> getCollectSensorEventMethod() {
    io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.SensorEventProto, com.google.protobuf.Empty> getCollectSensorEventMethod;
    if ((getCollectSensorEventMethod = CollectorControllerGrpc.getCollectSensorEventMethod) == null) {
      synchronized (CollectorControllerGrpc.class) {
        if ((getCollectSensorEventMethod = CollectorControllerGrpc.getCollectSensorEventMethod) == null) {
          CollectorControllerGrpc.getCollectSensorEventMethod = getCollectSensorEventMethod =
              io.grpc.MethodDescriptor.<ru.yandex.practicum.grpc.telemetry.event.SensorEventProto, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CollectSensorEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.yandex.practicum.grpc.telemetry.event.SensorEventProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorControllerMethodDescriptorSupplier("CollectSensorEvent"))
              .build();
        }
      }
    }
    return getCollectSensorEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.HubEventProto,
      com.google.protobuf.Empty> getCollectHubEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CollectHubEvent",
      requestType = ru.yandex.practicum.grpc.telemetry.event.HubEventProto.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.HubEventProto,
      com.google.protobuf.Empty> getCollectHubEventMethod() {
    io.grpc.MethodDescriptor<ru.yandex.practicum.grpc.telemetry.event.HubEventProto, com.google.protobuf.Empty> getCollectHubEventMethod;
    if ((getCollectHubEventMethod = CollectorControllerGrpc.getCollectHubEventMethod) == null) {
      synchronized (CollectorControllerGrpc.class) {
        if ((getCollectHubEventMethod = CollectorControllerGrpc.getCollectHubEventMethod) == null) {
          CollectorControllerGrpc.getCollectHubEventMethod = getCollectHubEventMethod =
              io.grpc.MethodDescriptor.<ru.yandex.practicum.grpc.telemetry.event.HubEventProto, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CollectHubEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.yandex.practicum.grpc.telemetry.event.HubEventProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorControllerMethodDescriptorSupplier("CollectHubEvent"))
              .build();
        }
      }
    }
    return getCollectHubEventMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CollectorControllerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorControllerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorControllerStub>() {
        @java.lang.Override
        public CollectorControllerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorControllerStub(channel, callOptions);
        }
      };
    return CollectorControllerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CollectorControllerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorControllerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorControllerBlockingStub>() {
        @java.lang.Override
        public CollectorControllerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorControllerBlockingStub(channel, callOptions);
        }
      };
    return CollectorControllerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CollectorControllerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorControllerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorControllerFutureStub>() {
        @java.lang.Override
        public CollectorControllerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorControllerFutureStub(channel, callOptions);
        }
      };
    return CollectorControllerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void collectSensorEvent(ru.yandex.practicum.grpc.telemetry.event.SensorEventProto request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCollectSensorEventMethod(), responseObserver);
    }

    /**
     */
    default void collectHubEvent(ru.yandex.practicum.grpc.telemetry.event.HubEventProto request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCollectHubEventMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CollectorController.
   */
  public static abstract class CollectorControllerImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CollectorControllerGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CollectorController.
   */
  public static final class CollectorControllerStub
      extends io.grpc.stub.AbstractAsyncStub<CollectorControllerStub> {
    private CollectorControllerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorControllerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorControllerStub(channel, callOptions);
    }

    /**
     */
    public void collectSensorEvent(ru.yandex.practicum.grpc.telemetry.event.SensorEventProto request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCollectSensorEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void collectHubEvent(ru.yandex.practicum.grpc.telemetry.event.HubEventProto request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCollectHubEventMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CollectorController.
   */
  public static final class CollectorControllerBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CollectorControllerBlockingStub> {
    private CollectorControllerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorControllerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorControllerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty collectSensorEvent(ru.yandex.practicum.grpc.telemetry.event.SensorEventProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCollectSensorEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty collectHubEvent(ru.yandex.practicum.grpc.telemetry.event.HubEventProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCollectHubEventMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CollectorController.
   */
  public static final class CollectorControllerFutureStub
      extends io.grpc.stub.AbstractFutureStub<CollectorControllerFutureStub> {
    private CollectorControllerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorControllerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorControllerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> collectSensorEvent(
        ru.yandex.practicum.grpc.telemetry.event.SensorEventProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCollectSensorEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> collectHubEvent(
        ru.yandex.practicum.grpc.telemetry.event.HubEventProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCollectHubEventMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_COLLECT_SENSOR_EVENT = 0;
  private static final int METHODID_COLLECT_HUB_EVENT = 1;

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
        case METHODID_COLLECT_SENSOR_EVENT:
          serviceImpl.collectSensorEvent((ru.yandex.practicum.grpc.telemetry.event.SensorEventProto) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_COLLECT_HUB_EVENT:
          serviceImpl.collectHubEvent((ru.yandex.practicum.grpc.telemetry.event.HubEventProto) request,
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
          getCollectSensorEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ru.yandex.practicum.grpc.telemetry.event.SensorEventProto,
              com.google.protobuf.Empty>(
                service, METHODID_COLLECT_SENSOR_EVENT)))
        .addMethod(
          getCollectHubEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ru.yandex.practicum.grpc.telemetry.event.HubEventProto,
              com.google.protobuf.Empty>(
                service, METHODID_COLLECT_HUB_EVENT)))
        .build();
  }

  private static abstract class CollectorControllerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CollectorControllerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CollectorController");
    }
  }

  private static final class CollectorControllerFileDescriptorSupplier
      extends CollectorControllerBaseDescriptorSupplier {
    CollectorControllerFileDescriptorSupplier() {}
  }

  private static final class CollectorControllerMethodDescriptorSupplier
      extends CollectorControllerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CollectorControllerMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CollectorControllerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CollectorControllerFileDescriptorSupplier())
              .addMethod(getCollectSensorEventMethod())
              .addMethod(getCollectHubEventMethod())
              .build();
        }
      }
    }
    return result;
  }
}
