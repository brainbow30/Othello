package application;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link Position}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutablePosition.builder()}.
 */
@Generated(from = "Position", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutablePosition implements Position {
  private final Integer x;
  private final Integer y;

  private ImmutablePosition(Integer x, Integer y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return The value of the {@code x} attribute
   */
  @Override
  public Integer x() {
    return x;
  }

  /**
   * @return The value of the {@code y} attribute
   */
  @Override
  public Integer y() {
    return y;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Position#x() x} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for x
   * @return A modified copy of the {@code this} object
   */
  public final ImmutablePosition withX(Integer value) {
    Integer newValue = Objects.requireNonNull(value, "x");
    if (this.x.equals(newValue)) return this;
    return new ImmutablePosition(newValue, this.y);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Position#y() y} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for y
   * @return A modified copy of the {@code this} object
   */
  public final ImmutablePosition withY(Integer value) {
    Integer newValue = Objects.requireNonNull(value, "y");
    if (this.y.equals(newValue)) return this;
    return new ImmutablePosition(this.x, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutablePosition} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutablePosition
        && equalTo((ImmutablePosition) another);
  }

  private boolean equalTo(ImmutablePosition another) {
    return x.equals(another.x)
        && y.equals(another.y);
  }

  /**
   * Computes a hash code from attributes: {@code x}, {@code y}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + x.hashCode();
    h += (h << 5) + y.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Position} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Position")
        .omitNullValues()
        .add("x", x)
        .add("y", y)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link Position} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Position instance
   */
  public static ImmutablePosition copyOf(Position instance) {
    if (instance instanceof ImmutablePosition) {
      return (ImmutablePosition) instance;
    }
    return ImmutablePosition.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutablePosition ImmutablePosition}.
   * <pre>
   * ImmutablePosition.builder()
   *    .x(Integer) // required {@link Position#x() x}
   *    .y(Integer) // required {@link Position#y() y}
   *    .build();
   * </pre>
   * @return A new ImmutablePosition builder
   */
  public static ImmutablePosition.Builder builder() {
    return new ImmutablePosition.Builder();
  }

  /**
   * Builds instances of type {@link ImmutablePosition ImmutablePosition}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Position", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_X = 0x1L;
    private static final long INIT_BIT_Y = 0x2L;
    private long initBits = 0x3L;

    private @Nullable Integer x;
    private @Nullable Integer y;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Position} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(Position instance) {
      Objects.requireNonNull(instance, "instance");
      x(instance.x());
      y(instance.y());
      return this;
    }

    /**
     * Initializes the value for the {@link Position#x() x} attribute.
     * @param x The value for x 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder x(Integer x) {
      this.x = Objects.requireNonNull(x, "x");
      initBits &= ~INIT_BIT_X;
      return this;
    }

    /**
     * Initializes the value for the {@link Position#y() y} attribute.
     * @param y The value for y 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder y(Integer y) {
      this.y = Objects.requireNonNull(y, "y");
      initBits &= ~INIT_BIT_Y;
      return this;
    }

    /**
     * Builds a new {@link ImmutablePosition ImmutablePosition}.
     * @return An immutable instance of Position
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutablePosition build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutablePosition(x, y);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_X) != 0) attributes.add("x");
      if ((initBits & INIT_BIT_Y) != 0) attributes.add("y");
      return "Cannot build Position, some of required attributes are not set " + attributes;
    }
  }
}
