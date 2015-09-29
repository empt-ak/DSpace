/**
 * The purpose of this package is to provide basic interfaces for so called modules.
 * Module is specific unit (part of metadata editor file structure) which:
 * <ul>
 * <li>resolves file structure into tree which is later mapped into given dspace hierarchy using ObjectWrapperResolver</li>
 * <li>provides CommunityProcessor which is used for converting xml files (or any other metadata representation) into objects, or using xpath into DSpace object metadata</li>
 * <li>provides CollectionProcessor which is used for converting xml files (or any other metadata representation) into objects, or using xpath into DSpace object metadata</li>
 * <li>provides ItemProcessor which is used for converting xml files (or any other metadata representation) into objects, or using xpath into DSpace object metadata</li>
 * </ul>
 * These four interfaces has to be implemented in order to be properly called during import.
 */
package cz.muni.ics.dspace5.api.module;
