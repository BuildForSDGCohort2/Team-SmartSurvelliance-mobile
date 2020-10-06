package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the UserDetails type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserDetails")
public final class UserDetails implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField FCM_REGISTRATION_ID = field("fcmRegistrationId");
  public static final QueryField USER_ID = field("userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") List<String> fcmRegistrationId;
  private final @ModelField(targetType="String") String userId;
  public String getId() {
      return id;
  }
  
  public List<String> getFcmRegistrationId() {
      return fcmRegistrationId;
  }
  
  public String getUserId() {
      return userId;
  }
  
  private UserDetails(String id, List<String> fcmRegistrationId, String userId) {
    this.id = id;
    this.fcmRegistrationId = fcmRegistrationId;
    this.userId = userId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserDetails userDetails = (UserDetails) obj;
      return ObjectsCompat.equals(getId(), userDetails.getId()) &&
              ObjectsCompat.equals(getFcmRegistrationId(), userDetails.getFcmRegistrationId()) &&
              ObjectsCompat.equals(getUserId(), userDetails.getUserId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFcmRegistrationId())
      .append(getUserId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserDetails {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("fcmRegistrationId=" + String.valueOf(getFcmRegistrationId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static UserDetails justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UserDetails(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      fcmRegistrationId,
      userId);
  }
  public interface BuildStep {
    UserDetails build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep fcmRegistrationId(List<String> fcmRegistrationId);
    BuildStep userId(String userId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private List<String> fcmRegistrationId;
    private String userId;
    @Override
     public UserDetails build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserDetails(
          id,
          fcmRegistrationId,
          userId);
    }
    
    @Override
     public BuildStep fcmRegistrationId(List<String> fcmRegistrationId) {
        this.fcmRegistrationId = fcmRegistrationId;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        this.userId = userId;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, List<String> fcmRegistrationId, String userId) {
      super.id(id);
      super.fcmRegistrationId(fcmRegistrationId)
        .userId(userId);
    }
    
    @Override
     public CopyOfBuilder fcmRegistrationId(List<String> fcmRegistrationId) {
      return (CopyOfBuilder) super.fcmRegistrationId(fcmRegistrationId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
  }
  
}
