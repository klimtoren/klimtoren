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
public class PartyResourcesRecord extends org.jooq.impl.UpdatableRecordImpl<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> implements org.jooq.Record6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, be.wolkmaan.klimtoren.heart.enums.Kind, java.lang.Integer, java.lang.Integer> {

	private static final long serialVersionUID = -131700344;

	/**
	 * Setter for <code>public.party_resources.id</code>.
	 */
	public PartyResourcesRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.party_resources.start_datetime</code>.
	 */
	public PartyResourcesRecord setStartDatetime(java.time.LocalDateTime value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.start_datetime</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.time.LocalDateTime getStartDatetime() {
		return (java.time.LocalDateTime) getValue(1);
	}

	/**
	 * Setter for <code>public.party_resources.end_datetime</code>.
	 */
	public PartyResourcesRecord setEndDatetime(java.time.LocalDateTime value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.end_datetime</code>.
	 */
	public java.time.LocalDateTime getEndDatetime() {
		return (java.time.LocalDateTime) getValue(2);
	}

	/**
	 * Setter for <code>public.party_resources.kind</code>.
	 */
	public PartyResourcesRecord setKind(be.wolkmaan.klimtoren.heart.enums.Kind value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.kind</code>.
	 */
	public be.wolkmaan.klimtoren.heart.enums.Kind getKind() {
		return (be.wolkmaan.klimtoren.heart.enums.Kind) getValue(3);
	}

	/**
	 * Setter for <code>public.party_resources.forparty</code>.
	 */
	public PartyResourcesRecord setForparty(java.lang.Integer value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.forparty</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getForparty() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>public.party_resources.useresource</code>.
	 */
	public PartyResourcesRecord setUseresource(java.lang.Integer value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>public.party_resources.useresource</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getUseresource() {
		return (java.lang.Integer) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, be.wolkmaan.klimtoren.heart.enums.Kind, java.lang.Integer, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.time.LocalDateTime, java.time.LocalDateTime, be.wolkmaan.klimtoren.heart.enums.Kind, java.lang.Integer, java.lang.Integer> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field2() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.START_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.time.LocalDateTime> field3() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.END_DATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<be.wolkmaan.klimtoren.heart.enums.Kind> field4() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.KIND;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.FORPARTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES.USERESOURCE;
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
	public java.time.LocalDateTime value2() {
		return getStartDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.time.LocalDateTime value3() {
		return getEndDatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.enums.Kind value4() {
		return getKind();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getForparty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getUseresource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value2(java.time.LocalDateTime value) {
		setStartDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value3(java.time.LocalDateTime value) {
		setEndDatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value4(be.wolkmaan.klimtoren.heart.enums.Kind value) {
		setKind(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value5(java.lang.Integer value) {
		setForparty(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord value6(java.lang.Integer value) {
		setUseresource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PartyResourcesRecord values(java.lang.Integer value1, java.time.LocalDateTime value2, java.time.LocalDateTime value3, be.wolkmaan.klimtoren.heart.enums.Kind value4, java.lang.Integer value5, java.lang.Integer value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PartyResourcesRecord
	 */
	public PartyResourcesRecord() {
		super(be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES);
	}

	/**
	 * Create a detached, initialised PartyResourcesRecord
	 */
	public PartyResourcesRecord(java.lang.Integer id, java.time.LocalDateTime startDatetime, java.time.LocalDateTime endDatetime, be.wolkmaan.klimtoren.heart.enums.Kind kind, java.lang.Integer forparty, java.lang.Integer useresource) {
		super(be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES);

		setValue(0, id);
		setValue(1, startDatetime);
		setValue(2, endDatetime);
		setValue(3, kind);
		setValue(4, forparty);
		setValue(5, useresource);
	}
}
