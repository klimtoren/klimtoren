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
public class Site extends org.jooq.impl.TableImpl<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord> {

	private static final long serialVersionUID = 723909128;

	/**
	 * The singleton instance of <code>public.site</code>
	 */
	public static final be.wolkmaan.klimtoren.heart.model.tables.Site SITE = new be.wolkmaan.klimtoren.heart.model.tables.Site();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord> getRecordType() {
		return be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord.class;
	}

	/**
	 * The column <code>public.site.id</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.site.displayname</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, java.lang.String> DISPLAYNAME = createField("displayname", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>public.site.descriptiveinformation</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, java.lang.String> DESCRIPTIVEINFORMATION = createField("descriptiveinformation", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>public.site.customtype</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, java.lang.String> CUSTOMTYPE = createField("customtype", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>public.site</code> table reference
	 */
	public Site() {
		this("site", null);
	}

	/**
	 * Create an aliased <code>public.site</code> table reference
	 */
	public Site(java.lang.String alias) {
		this(alias, be.wolkmaan.klimtoren.heart.model.tables.Site.SITE);
	}

	private Site(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord> aliased) {
		this(alias, aliased, null);
	}

	private Site(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, be.wolkmaan.klimtoren.heart.model.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord> getPrimaryKey() {
		return be.wolkmaan.klimtoren.heart.model.Keys.SITE_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord>>asList(be.wolkmaan.klimtoren.heart.model.Keys.SITE_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.SiteRecord, ?>>asList(be.wolkmaan.klimtoren.heart.model.Keys.SITE__SITE_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.model.tables.Site as(java.lang.String alias) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Site(alias, this);
	}

	/**
	 * Rename this table
	 */
	public be.wolkmaan.klimtoren.heart.model.tables.Site rename(java.lang.String name) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Site(name, null);
	}
}
