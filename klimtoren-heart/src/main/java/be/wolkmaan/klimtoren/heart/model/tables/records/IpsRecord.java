/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IpsRecord extends org.jooq.impl.UpdatableRecordImpl<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> implements org.jooq.Record3<java.lang.Integer, java.lang.Object, java.lang.String> {

	private static final long serialVersionUID = -121608551;

	/**
	 * Setter for <code>public.ips.id</code>.
	 */
	public IpsRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.ips.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.ips.address</code>.
	 */
	public IpsRecord setAddress(java.lang.Object value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.ips.address</code>.
	 */
	public java.lang.Object getAddress() {
		return (java.lang.Object) getValue(1);
	}

	/**
	 * Setter for <code>public.ips.customtype</code>.
	 */
	public IpsRecord setCustomtype(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.ips.customtype</code>.
	 */
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getCustomtype() {
		return (java.lang.String) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.Object, java.lang.String> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.Object, java.lang.String> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Object> field2() {
		return be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS.ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS.CUSTOMTYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Object value2() {
		return getAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getCustomtype();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IpsRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IpsRecord value2(java.lang.Object value) {
		setAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IpsRecord value3(java.lang.String value) {
		setCustomtype(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IpsRecord values(java.lang.Integer value1, java.lang.Object value2, java.lang.String value3) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached IpsRecord
	 */
	public IpsRecord() {
		super(be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS);
	}

	/**
	 * Create a detached, initialised IpsRecord
	 */
	public IpsRecord(java.lang.Integer id, java.lang.Object address, java.lang.String customtype) {
		super(be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS);

		setValue(0, id);
		setValue(1, address);
		setValue(2, customtype);
	}
}
