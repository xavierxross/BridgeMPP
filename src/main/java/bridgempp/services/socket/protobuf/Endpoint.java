// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bridgempp.proto

package bridgempp.services.socket.protobuf;

/**
 * Protobuf type {@code bridgempp.Endpoint}
 */
public  final class Endpoint extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:bridgempp.Endpoint)
    EndpointOrBuilder {
  // Use Endpoint.newBuilder() to construct.
  private Endpoint(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Endpoint() {
    identifier_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  @SuppressWarnings("unused")
private Endpoint(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            identifier_ = s;
            break;
          }
          case 18: {
            bridgempp.services.socket.protobuf.Service.Builder subBuilder = null;
            if (service_ != null) {
              subBuilder = service_.toBuilder();
            }
            service_ = input.readMessage(bridgempp.services.socket.protobuf.Service.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(service_);
              service_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return bridgempp.services.socket.protobuf.BridgeProtoBuf.internal_static_bridgempp_Endpoint_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return bridgempp.services.socket.protobuf.BridgeProtoBuf.internal_static_bridgempp_Endpoint_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            bridgempp.services.socket.protobuf.Endpoint.class, bridgempp.services.socket.protobuf.Endpoint.Builder.class);
  }

  public static final int IDENTIFIER_FIELD_NUMBER = 1;
  private volatile java.lang.Object identifier_;
  /**
   * <code>optional string identifier = 1;</code>
   */
  public java.lang.String getIdentifier() {
    java.lang.Object ref = identifier_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      identifier_ = s;
      return s;
    }
  }
  /**
   * <code>optional string identifier = 1;</code>
   */
  public com.google.protobuf.ByteString
      getIdentifierBytes() {
    java.lang.Object ref = identifier_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      identifier_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SERVICE_FIELD_NUMBER = 2;
  private bridgempp.services.socket.protobuf.Service service_;
  /**
   * <code>optional .bridgempp.Service service = 2;</code>
   */
  public boolean hasService() {
    return service_ != null;
  }
  /**
   * <code>optional .bridgempp.Service service = 2;</code>
   */
  public bridgempp.services.socket.protobuf.Service getService() {
    return service_ == null ? bridgempp.services.socket.protobuf.Service.getDefaultInstance() : service_;
  }
  /**
   * <code>optional .bridgempp.Service service = 2;</code>
   */
  public bridgempp.services.socket.protobuf.ServiceOrBuilder getServiceOrBuilder() {
    return getService();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getIdentifierBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, identifier_);
    }
    if (service_ != null) {
      output.writeMessage(2, getService());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getIdentifierBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, identifier_);
    }
    if (service_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getService());
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof bridgempp.services.socket.protobuf.Endpoint)) {
      return super.equals(obj);
    }
    bridgempp.services.socket.protobuf.Endpoint other = (bridgempp.services.socket.protobuf.Endpoint) obj;

    boolean result = true;
    result = result && getIdentifier()
        .equals(other.getIdentifier());
    result = result && (hasService() == other.hasService());
    if (hasService()) {
      result = result && getService()
          .equals(other.getService());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
@java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + IDENTIFIER_FIELD_NUMBER;
    hash = (53 * hash) + getIdentifier().hashCode();
    if (hasService()) {
      hash = (37 * hash) + SERVICE_FIELD_NUMBER;
      hash = (53 * hash) + getService().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static bridgempp.services.socket.protobuf.Endpoint parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(bridgempp.services.socket.protobuf.Endpoint prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code bridgempp.Endpoint}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:bridgempp.Endpoint)
      bridgempp.services.socket.protobuf.EndpointOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return bridgempp.services.socket.protobuf.BridgeProtoBuf.internal_static_bridgempp_Endpoint_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return bridgempp.services.socket.protobuf.BridgeProtoBuf.internal_static_bridgempp_Endpoint_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              bridgempp.services.socket.protobuf.Endpoint.class, bridgempp.services.socket.protobuf.Endpoint.Builder.class);
    }

    // Construct using bridgempp.services.socket.protobuf.Endpoint.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      identifier_ = "";

      if (serviceBuilder_ == null) {
        service_ = null;
      } else {
        service_ = null;
        serviceBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return bridgempp.services.socket.protobuf.BridgeProtoBuf.internal_static_bridgempp_Endpoint_descriptor;
    }

    public bridgempp.services.socket.protobuf.Endpoint getDefaultInstanceForType() {
      return bridgempp.services.socket.protobuf.Endpoint.getDefaultInstance();
    }

    public bridgempp.services.socket.protobuf.Endpoint build() {
      bridgempp.services.socket.protobuf.Endpoint result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public bridgempp.services.socket.protobuf.Endpoint buildPartial() {
      bridgempp.services.socket.protobuf.Endpoint result = new bridgempp.services.socket.protobuf.Endpoint(this);
      result.identifier_ = identifier_;
      if (serviceBuilder_ == null) {
        result.service_ = service_;
      } else {
        result.service_ = serviceBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof bridgempp.services.socket.protobuf.Endpoint) {
        return mergeFrom((bridgempp.services.socket.protobuf.Endpoint)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(bridgempp.services.socket.protobuf.Endpoint other) {
      if (other == bridgempp.services.socket.protobuf.Endpoint.getDefaultInstance()) return this;
      if (!other.getIdentifier().isEmpty()) {
        identifier_ = other.identifier_;
        onChanged();
      }
      if (other.hasService()) {
        mergeService(other.getService());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      bridgempp.services.socket.protobuf.Endpoint parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (bridgempp.services.socket.protobuf.Endpoint) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object identifier_ = "";
    /**
     * <code>optional string identifier = 1;</code>
     */
    public java.lang.String getIdentifier() {
      java.lang.Object ref = identifier_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        identifier_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string identifier = 1;</code>
     */
    public com.google.protobuf.ByteString
        getIdentifierBytes() {
      java.lang.Object ref = identifier_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        identifier_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string identifier = 1;</code>
     */
    public Builder setIdentifier(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      identifier_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string identifier = 1;</code>
     */
    public Builder clearIdentifier() {
      
      identifier_ = getDefaultInstance().getIdentifier();
      onChanged();
      return this;
    }
    /**
     * <code>optional string identifier = 1;</code>
     */
    public Builder setIdentifierBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      identifier_ = value;
      onChanged();
      return this;
    }

    private bridgempp.services.socket.protobuf.Service service_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        bridgempp.services.socket.protobuf.Service, bridgempp.services.socket.protobuf.Service.Builder, bridgempp.services.socket.protobuf.ServiceOrBuilder> serviceBuilder_;
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public boolean hasService() {
      return serviceBuilder_ != null || service_ != null;
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public bridgempp.services.socket.protobuf.Service getService() {
      if (serviceBuilder_ == null) {
        return service_ == null ? bridgempp.services.socket.protobuf.Service.getDefaultInstance() : service_;
      } else {
        return serviceBuilder_.getMessage();
      }
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public Builder setService(bridgempp.services.socket.protobuf.Service value) {
      if (serviceBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        service_ = value;
        onChanged();
      } else {
        serviceBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public Builder setService(
        bridgempp.services.socket.protobuf.Service.Builder builderForValue) {
      if (serviceBuilder_ == null) {
        service_ = builderForValue.build();
        onChanged();
      } else {
        serviceBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public Builder mergeService(bridgempp.services.socket.protobuf.Service value) {
      if (serviceBuilder_ == null) {
        if (service_ != null) {
          service_ =
            bridgempp.services.socket.protobuf.Service.newBuilder(service_).mergeFrom(value).buildPartial();
        } else {
          service_ = value;
        }
        onChanged();
      } else {
        serviceBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public Builder clearService() {
      if (serviceBuilder_ == null) {
        service_ = null;
        onChanged();
      } else {
        service_ = null;
        serviceBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public bridgempp.services.socket.protobuf.Service.Builder getServiceBuilder() {
      
      onChanged();
      return getServiceFieldBuilder().getBuilder();
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    public bridgempp.services.socket.protobuf.ServiceOrBuilder getServiceOrBuilder() {
      if (serviceBuilder_ != null) {
        return serviceBuilder_.getMessageOrBuilder();
      } else {
        return service_ == null ?
            bridgempp.services.socket.protobuf.Service.getDefaultInstance() : service_;
      }
    }
    /**
     * <code>optional .bridgempp.Service service = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        bridgempp.services.socket.protobuf.Service, bridgempp.services.socket.protobuf.Service.Builder, bridgempp.services.socket.protobuf.ServiceOrBuilder> 
        getServiceFieldBuilder() {
      if (serviceBuilder_ == null) {
        serviceBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            bridgempp.services.socket.protobuf.Service, bridgempp.services.socket.protobuf.Service.Builder, bridgempp.services.socket.protobuf.ServiceOrBuilder>(
                getService(),
                getParentForChildren(),
                isClean());
        service_ = null;
      }
      return serviceBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:bridgempp.Endpoint)
  }

  // @@protoc_insertion_point(class_scope:bridgempp.Endpoint)
  private static final bridgempp.services.socket.protobuf.Endpoint DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new bridgempp.services.socket.protobuf.Endpoint();
  }

  public static bridgempp.services.socket.protobuf.Endpoint getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Endpoint>
      PARSER = new com.google.protobuf.AbstractParser<Endpoint>() {
    public Endpoint parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Endpoint(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Endpoint> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Endpoint> getParserForType() {
    return PARSER;
  }

  public bridgempp.services.socket.protobuf.Endpoint getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

