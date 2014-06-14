/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PartyResources extends org.jooq.impl.TableImpl<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> {

	private static final long serialVersionUID = 409641685;

	/**
	 * The singleton instance of <code>public.party_resources</code>
	 */
	public static final be.wolkmaan.klimtoren.heart.model.tables.PartyResources PARTY_RESOURCES = new be.wolkmaan.klimtoren.heart.model.tables.PartyResources();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> getRecordType() {
		return be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord.class;
	}

	/**
	 * The column <code>public.party_resources.id</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.party_resources.start_datetime</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.time.LocalDateTime> START_DATETIME = createField("start_datetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new be.wolkmaan.klimtoren.heart.enums.LocalDateTimeConverter());

	/**
	 * The column <code>public.party_resources.end_datetime</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.time.LocalDateTime> END_DATETIME = createField("end_datetime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new be.wolkmaan.klimtoren.heart.enums.LocalDateTimeConverter());

	/**
	 * The column <code>public.party_resources.kind</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, be.wolkmaan.klimtoren.heart.enums.Kind> KIND = createField("kind", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "", new be.wolkmaan.klimtoren.heart.enums.KindConverter());

	/**
	 * The column <code>public.party_resources.forparty</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.lang.Integer> FORPARTY = createField("forparty", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.party_resources.useresource</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.lang.Integer> USERESOURCE = createField("useresource", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>public.party_resources</code> table reference
	 */
	public PartyResources() {
		this("party_resources", null);
	}

	/**
	 * Create an aliased <code>public.party_resources</code> table reference
	 */
	public PartyResources(java.lang.String alias) {
		this(alias, be.wolkmaan.klimtoren.heart.model.tables.PartyResources.PARTY_RESOURCES);
	}

	private PartyResources(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> aliased) {
		this(alias, aliased, null);
	}

	private PartyResources(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, be.wolkmaan.klimtoren.heart.model.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, java.lang.Integer> getIdentity() {
		return be.wolkmaan.klimtoren.heart.model.Keys.IDENTITY_PARTY_RESOURCES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord> getPrimaryKey() {
		return be.wolkmaan.klimtoren.heart.model.Keys.PARTY_RESOURCES_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord>>asList(be.wolkmaan.klimtoren.heart.model.Keys.PARTY_RESOURCES_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.PartyResourcesRecord, ?>>asList(be.wolkmaan.klimtoren.heart.model.Keys.PARTY_RESOURCES__PARTY_RESOURCES_FORPARTY_FKEY, be.wolkmaan.klimtoren.heart.model.Keys.PARTY_RESOURCES__PARTY_RESOURCES_USERESOURCE_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.model.tables.PartyResources as(java.lang.String alias) {
		return new be.wolkmaan.klimtoren.heart.model.tables.PartyResources(alias, this);
	}

	/**
	 * Rename this table
	 */
	public be.wolkmaan.klimtoren.heart.model.tables.PartyResources rename(java.lang.String name) {
		return new be.wolkmaan.klimtoren.heart.model.tables.PartyResources(name, null);
	}
}
