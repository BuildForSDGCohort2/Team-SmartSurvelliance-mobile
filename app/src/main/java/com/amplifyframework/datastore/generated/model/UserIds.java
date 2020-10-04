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

/** This is an auto generated class representing the UserIds type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserIds")
public final class UserIds implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField USER_ID = field("userID");
  public static final QueryField PHONE_ID = field("phoneId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="String", isRequired = true) List<String> phoneId;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public List<String> getPhoneId() {
      return phoneId;
  }
  
  private UserIds(String id, String userID, List<String> phoneId) {
    this.id = id;
    this.userID = userID;
    this.phoneId = phoneId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserIds userIds = (UserIds) obj;
      return ObjectsCompat.equals(getId(), userIds.getId()) &&
              ObjectsCompat.equals(getUserId(), userIds.getUserId()) &&
              ObjectsCompat.equals(getPhoneId(), userIds.getPhoneId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getPhoneId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserIds {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("phoneId=" + String.valueOf(getPhoneId()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static UserIds justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new UserIds(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userID,
      phoneId);
  }
  public interface UserIdStep {
    PhoneIdStep userId(String userId);
  }
  

  public interface PhoneIdStep {
    BuildStep phoneId(List<String> phoneId);
  }
  

  public interface BuildStep {
    UserIds build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements UserIdStep, PhoneIdStep, BuildStep {
    private String id;
    private String userID;
    private List<String> phoneId;
    @Override
     public UserIds build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserIds(
          id,
          userID,
          phoneId);
    }
    
    @Override
     public PhoneIdStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public BuildStep phoneId(List<String> phoneId) {
        Objects.requireNonNull(phoneId);
        this.phoneId = phoneId;
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
    private CopyOfBuilder(String id, String userId, List<String> phoneId) {
      super.id(id);
      super.userId(userId)
        .phoneId(phoneId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder phoneId(List<String> phoneId) {
      return (CopyOfBuilder) super.phoneId(phoneId);
    }
  }
  
}
