/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package bgoug;  
@SuppressWarnings("all")
public class Developer extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Developer\",\"namespace\":\"bgoug\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"NONE\"},{\"name\":\"age\",\"type\":\"int\",\"default\":\"NONE\"},{\"name\":\"language\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"Java\"}]}");
  @Deprecated public java.lang.String name;
  @Deprecated public int age;
  @Deprecated public java.lang.String language;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return age;
    case 2: return language;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = (java.lang.String)value$; break;
    case 1: age = (java.lang.Integer)value$; break;
    case 2: language = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'age' field.
   */
  public java.lang.Integer getAge() {
    return age;
  }

  /**
   * Sets the value of the 'age' field.
   * @param value the value to set.
   */
  public void setAge(java.lang.Integer value) {
    this.age = value;
  }

  /**
   * Gets the value of the 'language' field.
   */
  public java.lang.String getLanguage() {
    return language;
  }

  /**
   * Sets the value of the 'language' field.
   * @param value the value to set.
   */
  public void setLanguage(java.lang.String value) {
    this.language = value;
  }

  /** Creates a new Developer RecordBuilder */
  public static bgoug.Developer.Builder newBuilder() {
    return new bgoug.Developer.Builder();
  }
  
  /** Creates a new Developer RecordBuilder by copying an existing Builder */
  public static bgoug.Developer.Builder newBuilder(bgoug.Developer.Builder other) {
    return new bgoug.Developer.Builder(other);
  }
  
  /** Creates a new Developer RecordBuilder by copying an existing Developer instance */
  public static bgoug.Developer.Builder newBuilder(bgoug.Developer other) {
    return new bgoug.Developer.Builder(other);
  }
  
  /**
   * RecordBuilder for Developer instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Developer>
    implements org.apache.avro.data.RecordBuilder<Developer> {

    private java.lang.String name;
    private int age;
    private java.lang.String language;

    /** Creates a new Builder */
    private Builder() {
      super(bgoug.Developer.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(bgoug.Developer.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Developer instance */
    private Builder(bgoug.Developer other) {
            super(bgoug.Developer.SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = (java.lang.String) data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.age)) {
        this.age = (java.lang.Integer) data().deepCopy(fields()[1].schema(), other.age);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.language)) {
        this.language = (java.lang.String) data().deepCopy(fields()[2].schema(), other.language);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'name' field */
    public java.lang.String getName() {
      return name;
    }
    
    /** Sets the value of the 'name' field */
    public bgoug.Developer.Builder setName(java.lang.String value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'name' field has been set */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'name' field */
    public bgoug.Developer.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'age' field */
    public java.lang.Integer getAge() {
      return age;
    }
    
    /** Sets the value of the 'age' field */
    public bgoug.Developer.Builder setAge(int value) {
      validate(fields()[1], value);
      this.age = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'age' field has been set */
    public boolean hasAge() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'age' field */
    public bgoug.Developer.Builder clearAge() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'language' field */
    public java.lang.String getLanguage() {
      return language;
    }
    
    /** Sets the value of the 'language' field */
    public bgoug.Developer.Builder setLanguage(java.lang.String value) {
      validate(fields()[2], value);
      this.language = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'language' field has been set */
    public boolean hasLanguage() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'language' field */
    public bgoug.Developer.Builder clearLanguage() {
      language = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public Developer build() {
      try {
        Developer record = new Developer();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.String) defaultValue(fields()[0]);
        record.age = fieldSetFlags()[1] ? this.age : (java.lang.Integer) defaultValue(fields()[1]);
        record.language = fieldSetFlags()[2] ? this.language : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
