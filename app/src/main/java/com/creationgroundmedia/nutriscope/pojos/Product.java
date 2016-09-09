/*
 * Copyright 2016 George Cohn III
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creationgroundmedia.nutriscope.pojos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Date;

/**
 * Created by George Cohn III on 6/27/16.
 * Generated from API sample query result
 * using {@link <a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>}
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonProperty("labels_hierarchy")
    private List<String> labelsHierarchy;
    @JsonProperty("_id")
    private String Id;
    @JsonProperty("categories_hierarchy")
    private List<String> categoriesHierarchy;
    @JsonProperty("pnns_groups_1")
    private String pnnsGroups1;
    @JsonProperty("checkers_tags")
    private List<String> checkersTags;
    @JsonProperty("labels_tags")
    private List<String> labelsTags;
    @JsonProperty("image_small_url")
    private String imageSmallUrl;
    @JsonProperty("additives_tags_n")
    private String additivesTagsN;
    private String code;
    @JsonProperty("traces_tags")
    private List<String> tracesTags;
    @JsonProperty("ingredients_that_may_be_from_palm_oil_tags")
    private List<String> ingredientsThatMayBeFromPalmOilTags;
    @JsonProperty("additives_prev_n")
    private int additivesPrevN;
    private int rev;
    @JsonProperty("_keywords")
    private List<String> Keywords;
    @JsonProperty("interface_version_created")
    private String interfaceVersionCreated;
    @JsonProperty("max_imgid")
    private String maxImgid;
    @JsonProperty("unknown_nutrients_tags")
    private List<String> unknownNutrientsTags;
    @JsonProperty("categories_prev_tags")
    private List<String> categoriesPrevTags;
    @JsonProperty("ingredients_n")
    private String ingredientsN;
    @JsonProperty("purchase_places")
    private String purchasePlaces;
    @JsonProperty("emb_codes_tags")
    private List<String> embCodesTags;
    private String traces;
    @JsonProperty("image_nutrition_url")
    private String imageNutritionUrl;
    @JsonProperty("editors_tags")
    private List<String> editorsTags;
    @JsonProperty("labels_prev_tags")
    private List<String> labelsPrevTags;
    @JsonProperty("additives_old_n")
    private int additivesOldN;
    @JsonProperty("categories_prev_hierarchy")
    private List<String> categoriesPrevHierarchy;
    @JsonProperty("last_image_t")
    private int lastImageT;
    private String creator;
    @JsonProperty("serving_size")
    private String servingSize;
    @JsonProperty("new_additives_n")
    private int newAdditivesN;
    private String origins;
    private String stores;
    @JsonProperty("nutrition_grade_fr")
    private String nutritionGradeFr;
    @JsonProperty("nutrient_levels")
    private NutrientLevels nutrientLevels;
    @JsonProperty("nutrition_grades")
    private String nutritionGrades;
    @JsonProperty("additives_prev")
    private String additivesPrev;
    private String id;
    @JsonProperty("stores_tags")
    private List<String> storesTags;
    private String countries;
    @JsonProperty("fruits-vegetables-nuts_100g_estimate")
    private int fruitsVegetablesNuts100gEstimate;
    @JsonProperty("image_thumb_url")
    private String imageThumbUrl;
    @JsonProperty("image_nutrition_thumb_url")
    private String imageNutritionThumbUrl;
    @JsonProperty("last_modified_t")
    private int lastModifiedT;
    @JsonProperty("image_ingredients_url")
    private String imageIngredientsUrl;
    @JsonProperty("ingredients_text_en")
    private String ingredientsTextEn;
    private String brands;
    @JsonProperty("categories_debug_tags")
    private List<String> categoriesDebugTags;
    @JsonProperty("languages_tags")
    private List<String> languagesTags;
    @JsonProperty("generic_name_en")
    private String genericNameEn;
    @JsonProperty("codes_tags")
    private List<String> codesTags;
    @JsonProperty("languages_codes")
    private LanguagesCodes languagesCodes;
    @JsonProperty("nutrition_grades_tags")
    private List<String> nutritionGradesTags;
    @JsonProperty("last_image_dates_tags")
    private List<String> lastImageDatesTags;
    private String packaging;
    @JsonProperty("serving_quantity")
    private int servingQuantity;
    private Languages languages;
    @JsonProperty("origins_tags")
    private List<String> originsTags;
    @JsonProperty("nutrition_data_per")
    private String nutritionDataPer;
    @JsonProperty("cities_tags")
    private List<String> citiesTags;
    @JsonProperty("languages_hierarchy")
    private List<String> languagesHierarchy;
    @JsonProperty("emb_codes_20141016")
    private String embCodes20141016;
    @JsonProperty("expiration_date")
    private String expirationDate;
    @JsonProperty("ingredients_that_may_be_from_palm_oil_n")
    private int ingredientsThatMayBeFromPalmOilN;
    @JsonProperty("allergens_tags")
    private List<String> allergensTags;
    @JsonProperty("ingredients_from_palm_oil_n")
    private int ingredientsFromPalmOilN;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("ingredients_debug")
    private List<String> ingredientsDebug;
    private String lc;
    private List<Ingredients> ingredients;
    @JsonProperty("pnns_groups_1_tags")
    private List<String> pnnsGroups1Tags;
    @JsonProperty("last_editor")
    private String lastEditor;
    private int complete;
    @JsonProperty("last_edit_dates_tags")
    private List<String> lastEditDatesTags;
    @JsonProperty("image_front_small_url")
    private String imageFrontSmallUrl;
    @JsonProperty("labels_debug_tags")
    private List<String> labelsDebugTags;
    @JsonProperty("states_tags")
    private List<String> statesTags;
    private String lang;
    private List<String> photographers;
    @JsonProperty("generic_name")
    private String genericName;
    @JsonProperty("emb_codes")
    private String embCodes;
    private List<String> editors;
    @JsonProperty("additives_tags")
    private List<String> additivesTags;
    @JsonProperty("emb_codes_orig")
    private String embCodesOrig;
    @JsonProperty("nutrient_levels_tags")
    private List<String> nutrientLevelsTags;
    @JsonProperty("informers_tags")
    private List<String> informersTags;
    @JsonProperty("additives_n")
    private int additivesN;
    @JsonProperty("photographers_tags")
    private List<String> photographersTags;
    @JsonProperty("allergens_hierarchy")
    private List<String> allergensHierarchy;
    @JsonProperty("pnns_groups_2_tags")
    private List<String> pnnsGroups2Tags;
    @JsonProperty("packaging_tags")
    private List<String> packagingTags;
    private Nutriments nutriments;
    @JsonProperty("countries_tags")
    private List<String> countriesTags;
    @JsonProperty("ingredients_from_palm_oil_tags")
    private List<String> ingredientsFromPalmOilTags;
    @JsonProperty("brands_tags")
    private List<String> brandsTags;
    @JsonProperty("additives_old_tags")
    private List<String> additivesOldTags;
    @JsonProperty("countries_hierarchy")
    private List<String> countriesHierarchy;
    @JsonProperty("pnns_groups_2")
    private String pnnsGroups2;
    private String categories;
    @JsonProperty("ingredients_text_debug")
    private String ingredientsTextDebug;
    @JsonProperty("ingredients_text")
    private String ingredientsText;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("created_t")
    private int createdT;
    @JsonProperty("ingredients_from_or_that_may_be_from_palm_oil_n")
    private int ingredientsFromOrThatMayBeFromPalmOilN;
    @JsonProperty("no_nutrition_data")
    private String noNutritionData;
    @JsonProperty("image_front_url")
    private String imageFrontUrl;
    @JsonProperty("last_modified_by")
    private String lastModifiedBy;
    @JsonProperty("completed_t")
    private int completedT;
    @JsonProperty("additives_prev_tags")
    private List<String> additivesPrevTags;
    private String allergens;
    @JsonProperty("product_name_en")
    private String productNameEn;
    @JsonProperty("image_front_thumb_url")
    private String imageFrontThumbUrl;
    @JsonProperty("traces_hierarchy")
    private List<String> tracesHierarchy;
    @JsonProperty("purchase_places_tags")
    private List<String> purchasePlacesTags;
    @JsonProperty("interface_version_modified")
    private String interfaceVersionModified;
    private int sortkey;
    @JsonProperty("nutrition_score_debug")
    private String nutritionScoreDebug;
    @JsonProperty("image_nutrition_small_url")
    private String imageNutritionSmallUrl;
    @JsonProperty("correctors_tags")
    private List<String> correctorsTags;
    private List<String> correctors;
    @JsonProperty("ingredients_n_tags")
    private List<String> ingredientsNTags;
    @JsonProperty("ingredients_tags")
    private List<String> ingredientsTags;
    private List<String> informers;
    private String states;
    @JsonProperty("entry_dates_tags")
    private List<String> entryDatesTags;
    @JsonProperty("image_ingredients_small_url")
    private String imageIngredientsSmallUrl;
    @JsonProperty("ingredients_text_with_allergens")
    private String ingredientsTextWithAllergens;
    private String labels;
    @JsonProperty("categories_tags")
    private List<String> categoriesTags;
    @JsonProperty("labels_prev_hierarchy")
    private List<String> labelsPrevHierarchy;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("states_hierarchy")
    private List<String> statesHierarchy;
    @JsonProperty("image_ingredients_thumb_url")
    private String imageIngredientsThumbUrl;
    @JsonProperty("ingredients_text_with_allergens_en")
    private String ingredientsTextWithAllergensEn;
    private List<String> checkers;
    private String additives;
    @JsonProperty("ingredients_ids_debug")
    private List<String> ingredientsIdsDebug;
    @JsonProperty("additives_debug_tags")
    private List<String> additivesDebugTags;


    public void setLabelsHierarchy(List<String> labelsHierarchy) {
        this.labelsHierarchy = labelsHierarchy;
    }
    public List<String> getLabelsHierarchy() {
        return labelsHierarchy;
    }


    public void set_Id(String Id) {
        this.Id = Id;
    }
    public String get_Id() {
        return Id;
    }


    public void setCategoriesHierarchy(List<String> categoriesHierarchy) {
        this.categoriesHierarchy = categoriesHierarchy;
    }
    public List<String> getCategoriesHierarchy() {
        return categoriesHierarchy;
    }


    public void setPnnsGroups1(String pnnsGroups1) {
        this.pnnsGroups1 = pnnsGroups1;
    }
    public String getPnnsGroups1() {
        return pnnsGroups1;
    }


    public void setCheckersTags(List<String> checkersTags) {
        this.checkersTags = checkersTags;
    }
    public List<String> getCheckersTags() {
        return checkersTags;
    }


    public void setLabelsTags(List<String> labelsTags) {
        this.labelsTags = labelsTags;
    }
    public List<String> getLabelsTags() {
        return labelsTags;
    }


    public void setImageSmallUrl(String imageSmallUrl) {
        this.imageSmallUrl = imageSmallUrl;
    }
    public String getImageSmallUrl() {
        return imageSmallUrl;
    }


    public void setAdditivesTagsN(String additivesTagsN) {
        this.additivesTagsN = additivesTagsN;
    }
    public String getAdditivesTagsN() {
        return additivesTagsN;
    }


    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }


    public void setTracesTags(List<String> tracesTags) {
        this.tracesTags = tracesTags;
    }
    public List<String> getTracesTags() {
        return tracesTags;
    }


    public void setIngredientsThatMayBeFromPalmOilTags(List<String> ingredientsThatMayBeFromPalmOilTags) {
        this.ingredientsThatMayBeFromPalmOilTags = ingredientsThatMayBeFromPalmOilTags;
    }
    public List<String> getIngredientsThatMayBeFromPalmOilTags() {
        return ingredientsThatMayBeFromPalmOilTags;
    }


    public void setAdditivesPrevN(int additivesPrevN) {
        this.additivesPrevN = additivesPrevN;
    }
    public int getAdditivesPrevN() {
        return additivesPrevN;
    }


    public void setRev(int rev) {
        this.rev = rev;
    }
    public int getRev() {
        return rev;
    }


    public void setKeywords(List<String> Keywords) {
        this.Keywords = Keywords;
    }
    public List<String> getKeywords() {
        return Keywords;
    }


    public void setInterfaceVersionCreated(String interfaceVersionCreated) {
        this.interfaceVersionCreated = interfaceVersionCreated;
    }
    public String getInterfaceVersionCreated() {
        return interfaceVersionCreated;
    }


    public void setMaxImgid(String maxImgid) {
        this.maxImgid = maxImgid;
    }
    public String getMaxImgid() {
        return maxImgid;
    }


    public void setUnknownNutrientsTags(List<String> unknownNutrientsTags) {
        this.unknownNutrientsTags = unknownNutrientsTags;
    }
    public List<String> getUnknownNutrientsTags() {
        return unknownNutrientsTags;
    }


    public void setCategoriesPrevTags(List<String> categoriesPrevTags) {
        this.categoriesPrevTags = categoriesPrevTags;
    }
    public List<String> getCategoriesPrevTags() {
        return categoriesPrevTags;
    }


    public void setIngredientsN(String ingredientsN) {
        this.ingredientsN = ingredientsN;
    }
    public String getIngredientsN() {
        return ingredientsN;
    }


    public void setPurchasePlaces(String purchasePlaces) {
        this.purchasePlaces = purchasePlaces;
    }
    public String getPurchasePlaces() {
        return purchasePlaces;
    }


    public void setEmbCodesTags(List<String> embCodesTags) {
        this.embCodesTags = embCodesTags;
    }
    public List<String> getEmbCodesTags() {
        return embCodesTags;
    }


    public void setTraces(String traces) {
        this.traces = traces;
    }
    public String getTraces() {
        return traces;
    }


    public void setImageNutritionUrl(String imageNutritionUrl) {
        this.imageNutritionUrl = imageNutritionUrl;
    }
    public String getImageNutritionUrl() {
        return imageNutritionUrl;
    }


    public void setEditorsTags(List<String> editorsTags) {
        this.editorsTags = editorsTags;
    }
    public List<String> getEditorsTags() {
        return editorsTags;
    }


    public void setLabelsPrevTags(List<String> labelsPrevTags) {
        this.labelsPrevTags = labelsPrevTags;
    }
    public List<String> getLabelsPrevTags() {
        return labelsPrevTags;
    }


    public void setAdditivesOldN(int additivesOldN) {
        this.additivesOldN = additivesOldN;
    }
    public int getAdditivesOldN() {
        return additivesOldN;
    }


    public void setCategoriesPrevHierarchy(List<String> categoriesPrevHierarchy) {
        this.categoriesPrevHierarchy = categoriesPrevHierarchy;
    }
    public List<String> getCategoriesPrevHierarchy() {
        return categoriesPrevHierarchy;
    }


    public void setLastImageT(int lastImageT) {
        this.lastImageT = lastImageT;
    }
    public int getLastImageT() {
        return lastImageT;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreator() {
        return creator;
    }


    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
    public String getServingSize() {
        return servingSize;
    }


    public void setNewAdditivesN(int newAdditivesN) {
        this.newAdditivesN = newAdditivesN;
    }
    public int getNewAdditivesN() {
        return newAdditivesN;
    }


    public void setOrigins(String origins) {
        this.origins = origins;
    }
    public String getOrigins() {
        return origins;
    }


    public void setStores(String stores) {
        this.stores = stores;
    }
    public String getStores() {
        return stores;
    }


    public void setNutritionGradeFr(String nutritionGradeFr) {
        this.nutritionGradeFr = nutritionGradeFr;
    }
    public String getNutritionGradeFr() {
        return nutritionGradeFr;
    }


    public void setNutrientLevels(NutrientLevels nutrientLevels) {
        this.nutrientLevels = nutrientLevels;
    }
    public NutrientLevels getNutrientLevels() {
        return nutrientLevels;
    }


    public void setNutritionGrades(String nutritionGrades) {
        this.nutritionGrades = nutritionGrades;
    }
    public String getNutritionGrades() {
        return nutritionGrades;
    }


    public void setAdditivesPrev(String additivesPrev) {
        this.additivesPrev = additivesPrev;
    }
    public String getAdditivesPrev() {
        return additivesPrev;
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }


    public void setStoresTags(List<String> storesTags) {
        this.storesTags = storesTags;
    }
    public List<String> getStoresTags() {
        return storesTags;
    }


    public void setCountries(String countries) {
        this.countries = countries;
    }
    public String getCountries() {
        return countries;
    }


    public void setFruitsVegetablesNuts100gEstimate(int fruitsVegetablesNuts100gEstimate) {
        this.fruitsVegetablesNuts100gEstimate = fruitsVegetablesNuts100gEstimate;
    }
    public int getFruitsVegetablesNuts100gEstimate() {
        return fruitsVegetablesNuts100gEstimate;
    }


    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }
    public String getImageThumbUrl() {
        return imageThumbUrl;
    }


    public void setImageNutritionThumbUrl(String imageNutritionThumbUrl) {
        this.imageNutritionThumbUrl = imageNutritionThumbUrl;
    }
    public String getImageNutritionThumbUrl() {
        return imageNutritionThumbUrl;
    }


    public void setLastModifiedT(int lastModifiedT) {
        this.lastModifiedT = lastModifiedT;
    }
    public int getLastModifiedT() {
        return lastModifiedT;
    }


    public void setImageIngredientsUrl(String imageIngredientsUrl) {
        this.imageIngredientsUrl = imageIngredientsUrl;
    }
    public String getImageIngredientsUrl() {
        return imageIngredientsUrl;
    }


    public void setIngredientsTextEn(String ingredientsTextEn) {
        this.ingredientsTextEn = ingredientsTextEn;
    }
    public String getIngredientsTextEn() {
        return ingredientsTextEn;
    }


    public void setBrands(String brands) {
        this.brands = brands;
    }
    public String getBrands() {
        return brands;
    }


    public void setCategoriesDebugTags(List<String> categoriesDebugTags) {
        this.categoriesDebugTags = categoriesDebugTags;
    }
    public List<String> getCategoriesDebugTags() {
        return categoriesDebugTags;
    }


    public void setLanguagesTags(List<String> languagesTags) {
        this.languagesTags = languagesTags;
    }
    public List<String> getLanguagesTags() {
        return languagesTags;
    }


    public void setGenericNameEn(String genericNameEn) {
        this.genericNameEn = genericNameEn;
    }
    public String getGenericNameEn() {
        return genericNameEn;
    }


    public void setCodesTags(List<String> codesTags) {
        this.codesTags = codesTags;
    }
    public List<String> getCodesTags() {
        return codesTags;
    }


    public void setLanguagesCodes(LanguagesCodes languagesCodes) {
        this.languagesCodes = languagesCodes;
    }
    public LanguagesCodes getLanguagesCodes() {
        return languagesCodes;
    }


    public void setNutritionGradesTags(List<String> nutritionGradesTags) {
        this.nutritionGradesTags = nutritionGradesTags;
    }
    public List<String> getNutritionGradesTags() {
        return nutritionGradesTags;
    }


    public void setLastImageDatesTags(List<String> lastImageDatesTags) {
        this.lastImageDatesTags = lastImageDatesTags;
    }
    public List<String> getLastImageDatesTags() {
        return lastImageDatesTags;
    }


    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }
    public String getPackaging() {
        return packaging;
    }


    public void setServingQuantity(int servingQuantity) {
        this.servingQuantity = servingQuantity;
    }
    public int getServingQuantity() {
        return servingQuantity;
    }


    public void setLanguages(Languages languages) {
        this.languages = languages;
    }
    public Languages getLanguages() {
        return languages;
    }


    public void setOriginsTags(List<String> originsTags) {
        this.originsTags = originsTags;
    }
    public List<String> getOriginsTags() {
        return originsTags;
    }


    public void setNutritionDataPer(String nutritionDataPer) {
        this.nutritionDataPer = nutritionDataPer;
    }
    public String getNutritionDataPer() {
        return nutritionDataPer;
    }


    public void setCitiesTags(List<String> citiesTags) {
        this.citiesTags = citiesTags;
    }
    public List<String> getCitiesTags() {
        return citiesTags;
    }


    public void setLanguagesHierarchy(List<String> languagesHierarchy) {
        this.languagesHierarchy = languagesHierarchy;
    }
    public List<String> getLanguagesHierarchy() {
        return languagesHierarchy;
    }


    public void setEmbCodes20141016(String embCodes20141016) {
        this.embCodes20141016 = embCodes20141016;
    }
    public String getEmbCodes20141016() {
        return embCodes20141016;
    }


    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getExpirationDate() {
        return expirationDate;
    }


    public void setIngredientsThatMayBeFromPalmOilN(int ingredientsThatMayBeFromPalmOilN) {
        this.ingredientsThatMayBeFromPalmOilN = ingredientsThatMayBeFromPalmOilN;
    }
    public int getIngredientsThatMayBeFromPalmOilN() {
        return ingredientsThatMayBeFromPalmOilN;
    }


    public void setAllergensTags(List<String> allergensTags) {
        this.allergensTags = allergensTags;
    }
    public List<String> getAllergensTags() {
        return allergensTags;
    }


    public void setIngredientsFromPalmOilN(int ingredientsFromPalmOilN) {
        this.ingredientsFromPalmOilN = ingredientsFromPalmOilN;
    }
    public int getIngredientsFromPalmOilN() {
        return ingredientsFromPalmOilN;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }


    public void setIngredientsDebug(List<String> ingredientsDebug) {
        this.ingredientsDebug = ingredientsDebug;
    }
    public List<String> getIngredientsDebug() {
        return ingredientsDebug;
    }


    public void setLc(String lc) {
        this.lc = lc;
    }
    public String getLc() {
        return lc;
    }


    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
    public List<Ingredients> getIngredients() {
        return ingredients;
    }


    public void setPnnsGroups1Tags(List<String> pnnsGroups1Tags) {
        this.pnnsGroups1Tags = pnnsGroups1Tags;
    }
    public List<String> getPnnsGroups1Tags() {
        return pnnsGroups1Tags;
    }


    public void setLastEditor(String lastEditor) {
        this.lastEditor = lastEditor;
    }
    public String getLastEditor() {
        return lastEditor;
    }


    public void setComplete(int complete) {
        this.complete = complete;
    }
    public int getComplete() {
        return complete;
    }


    public void setLastEditDatesTags(List<String> lastEditDatesTags) {
        this.lastEditDatesTags = lastEditDatesTags;
    }
    public List<String> getLastEditDatesTags() {
        return lastEditDatesTags;
    }


    public void setImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.imageFrontSmallUrl = imageFrontSmallUrl;
    }
    public String getImageFrontSmallUrl() {
        return imageFrontSmallUrl;
    }


    public void setLabelsDebugTags(List<String> labelsDebugTags) {
        this.labelsDebugTags = labelsDebugTags;
    }
    public List<String> getLabelsDebugTags() {
        return labelsDebugTags;
    }


    public void setStatesTags(List<String> statesTags) {
        this.statesTags = statesTags;
    }
    public List<String> getStatesTags() {
        return statesTags;
    }


    public void setLang(String lang) {
        this.lang = lang;
    }
    public String getLang() {
        return lang;
    }


    public void setPhotographers(List<String> photographers) {
        this.photographers = photographers;
    }
    public List<String> getPhotographers() {
        return photographers;
    }


    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
    public String getGenericName() {
        return genericName;
    }


    public void setEmbCodes(String embCodes) {
        this.embCodes = embCodes;
    }
    public String getEmbCodes() {
        return embCodes;
    }


    public void setEditors(List<String> editors) {
        this.editors = editors;
    }
    public List<String> getEditors() {
        return editors;
    }


    public void setAdditivesTags(List<String> additivesTags) {
        this.additivesTags = additivesTags;
    }
    public List<String> getAdditivesTags() {
        return additivesTags;
    }


    public void setEmbCodesOrig(String embCodesOrig) {
        this.embCodesOrig = embCodesOrig;
    }
    public String getEmbCodesOrig() {
        return embCodesOrig;
    }


    public void setNutrientLevelsTags(List<String> nutrientLevelsTags) {
        this.nutrientLevelsTags = nutrientLevelsTags;
    }
    public List<String> getNutrientLevelsTags() {
        return nutrientLevelsTags;
    }


    public void setInformersTags(List<String> informersTags) {
        this.informersTags = informersTags;
    }
    public List<String> getInformersTags() {
        return informersTags;
    }


    public void setAdditivesN(int additivesN) {
        this.additivesN = additivesN;
    }
    public int getAdditivesN() {
        return additivesN;
    }


    public void setPhotographersTags(List<String> photographersTags) {
        this.photographersTags = photographersTags;
    }
    public List<String> getPhotographersTags() {
        return photographersTags;
    }


    public void setAllergensHierarchy(List<String> allergensHierarchy) {
        this.allergensHierarchy = allergensHierarchy;
    }
    public List<String> getAllergensHierarchy() {
        return allergensHierarchy;
    }


    public void setPnnsGroups2Tags(List<String> pnnsGroups2Tags) {
        this.pnnsGroups2Tags = pnnsGroups2Tags;
    }
    public List<String> getPnnsGroups2Tags() {
        return pnnsGroups2Tags;
    }


    public void setPackagingTags(List<String> packagingTags) {
        this.packagingTags = packagingTags;
    }
    public List<String> getPackagingTags() {
        return packagingTags;
    }


    public void setNutriments(Nutriments nutriments) {
        this.nutriments = nutriments;
    }
    public Nutriments getNutriments() {
        return nutriments;
    }


    public void setCountriesTags(List<String> countriesTags) {
        this.countriesTags = countriesTags;
    }
    public List<String> getCountriesTags() {
        return countriesTags;
    }


    public void setIngredientsFromPalmOilTags(List<String> ingredientsFromPalmOilTags) {
        this.ingredientsFromPalmOilTags = ingredientsFromPalmOilTags;
    }
    public List<String> getIngredientsFromPalmOilTags() {
        return ingredientsFromPalmOilTags;
    }


    public void setBrandsTags(List<String> brandsTags) {
        this.brandsTags = brandsTags;
    }
    public List<String> getBrandsTags() {
        return brandsTags;
    }


    public void setAdditivesOldTags(List<String> additivesOldTags) {
        this.additivesOldTags = additivesOldTags;
    }
    public List<String> getAdditivesOldTags() {
        return additivesOldTags;
    }


    public void setCountriesHierarchy(List<String> countriesHierarchy) {
        this.countriesHierarchy = countriesHierarchy;
    }
    public List<String> getCountriesHierarchy() {
        return countriesHierarchy;
    }


    public void setPnnsGroups2(String pnnsGroups2) {
        this.pnnsGroups2 = pnnsGroups2;
    }
    public String getPnnsGroups2() {
        return pnnsGroups2;
    }


    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getCategories() {
        return categories;
    }


    public void setIngredientsTextDebug(String ingredientsTextDebug) {
        this.ingredientsTextDebug = ingredientsTextDebug;
    }
    public String getIngredientsTextDebug() {
        return ingredientsTextDebug;
    }


    public void setIngredientsText(String ingredientsText) {
        this.ingredientsText = ingredientsText;
    }
    public String getIngredientsText() {
        return ingredientsText;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }


    public void setCreatedT(int createdT) {
        this.createdT = createdT;
    }
    public int getCreatedT() {
        return createdT;
    }


    public void setIngredientsFromOrThatMayBeFromPalmOilN(int ingredientsFromOrThatMayBeFromPalmOilN) {
        this.ingredientsFromOrThatMayBeFromPalmOilN = ingredientsFromOrThatMayBeFromPalmOilN;
    }
    public int getIngredientsFromOrThatMayBeFromPalmOilN() {
        return ingredientsFromOrThatMayBeFromPalmOilN;
    }


    public void setNoNutritionData(String noNutritionData) {
        this.noNutritionData = noNutritionData;
    }
    public String getNoNutritionData() {
        return noNutritionData;
    }


    public void setImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
    }
    public String getImageFrontUrl() {
        return imageFrontUrl;
    }


    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }


    public void setCompletedT(int completedT) {
        this.completedT = completedT;
    }
    public int getCompletedT() {
        return completedT;
    }


    public void setAdditivesPrevTags(List<String> additivesPrevTags) {
        this.additivesPrevTags = additivesPrevTags;
    }
    public List<String> getAdditivesPrevTags() {
        return additivesPrevTags;
    }


    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
    public String getAllergens() {
        return allergens;
    }


    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }
    public String getProductNameEn() {
        return productNameEn;
    }


    public void setImageFrontThumbUrl(String imageFrontThumbUrl) {
        this.imageFrontThumbUrl = imageFrontThumbUrl;
    }
    public String getImageFrontThumbUrl() {
        return imageFrontThumbUrl;
    }


    public void setTracesHierarchy(List<String> tracesHierarchy) {
        this.tracesHierarchy = tracesHierarchy;
    }
    public List<String> getTracesHierarchy() {
        return tracesHierarchy;
    }


    public void setPurchasePlacesTags(List<String> purchasePlacesTags) {
        this.purchasePlacesTags = purchasePlacesTags;
    }
    public List<String> getPurchasePlacesTags() {
        return purchasePlacesTags;
    }


    public void setInterfaceVersionModified(String interfaceVersionModified) {
        this.interfaceVersionModified = interfaceVersionModified;
    }
    public String getInterfaceVersionModified() {
        return interfaceVersionModified;
    }


    public void setSortkey(int sortkey) {
        this.sortkey = sortkey;
    }
    public int getSortkey() {
        return sortkey;
    }


    public void setNutritionScoreDebug(String nutritionScoreDebug) {
        this.nutritionScoreDebug = nutritionScoreDebug;
    }
    public String getNutritionScoreDebug() {
        return nutritionScoreDebug;
    }


    public void setImageNutritionSmallUrl(String imageNutritionSmallUrl) {
        this.imageNutritionSmallUrl = imageNutritionSmallUrl;
    }
    public String getImageNutritionSmallUrl() {
        return imageNutritionSmallUrl;
    }


    public void setCorrectorsTags(List<String> correctorsTags) {
        this.correctorsTags = correctorsTags;
    }
    public List<String> getCorrectorsTags() {
        return correctorsTags;
    }


    public void setCorrectors(List<String> correctors) {
        this.correctors = correctors;
    }
    public List<String> getCorrectors() {
        return correctors;
    }


    public void setIngredientsNTags(List<String> ingredientsNTags) {
        this.ingredientsNTags = ingredientsNTags;
    }
    public List<String> getIngredientsNTags() {
        return ingredientsNTags;
    }


    public void setIngredientsTags(List<String> ingredientsTags) {
        this.ingredientsTags = ingredientsTags;
    }
    public List<String> getIngredientsTags() {
        return ingredientsTags;
    }


    public void setInformers(List<String> informers) {
        this.informers = informers;
    }
    public List<String> getInformers() {
        return informers;
    }


    public void setStates(String states) {
        this.states = states;
    }
    public String getStates() {
        return states;
    }


    public void setEntryDatesTags(List<String> entryDatesTags) {
        this.entryDatesTags = entryDatesTags;
    }
    public List<String> getEntryDatesTags() {
        return entryDatesTags;
    }


    public void setImageIngredientsSmallUrl(String imageIngredientsSmallUrl) {
        this.imageIngredientsSmallUrl = imageIngredientsSmallUrl;
    }
    public String getImageIngredientsSmallUrl() {
        return imageIngredientsSmallUrl;
    }


    public void setIngredientsTextWithAllergens(String ingredientsTextWithAllergens) {
        this.ingredientsTextWithAllergens = ingredientsTextWithAllergens;
    }
    public String getIngredientsTextWithAllergens() {
        return ingredientsTextWithAllergens;
    }


    public void setLabels(String labels) {
        this.labels = labels;
    }
    public String getLabels() {
        return labels;
    }


    public void setCategoriesTags(List<String> categoriesTags) {
        this.categoriesTags = categoriesTags;
    }
    public List<String> getCategoriesTags() {
        return categoriesTags;
    }


    public void setLabelsPrevHierarchy(List<String> labelsPrevHierarchy) {
        this.labelsPrevHierarchy = labelsPrevHierarchy;
    }
    public List<String> getLabelsPrevHierarchy() {
        return labelsPrevHierarchy;
    }


    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getQuantity() {
        return quantity;
    }


    public void setStatesHierarchy(List<String> statesHierarchy) {
        this.statesHierarchy = statesHierarchy;
    }
    public List<String> getStatesHierarchy() {
        return statesHierarchy;
    }


    public void setImageIngredientsThumbUrl(String imageIngredientsThumbUrl) {
        this.imageIngredientsThumbUrl = imageIngredientsThumbUrl;
    }
    public String getImageIngredientsThumbUrl() {
        return imageIngredientsThumbUrl;
    }


    public void setIngredientsTextWithAllergensEn(String ingredientsTextWithAllergensEn) {
        this.ingredientsTextWithAllergensEn = ingredientsTextWithAllergensEn;
    }
    public String getIngredientsTextWithAllergensEn() {
        return ingredientsTextWithAllergensEn;
    }


    public void setCheckers(List<String> checkers) {
        this.checkers = checkers;
    }
    public List<String> getCheckers() {
        return checkers;
    }


    public void setAdditives(String additives) {
        this.additives = additives;
    }
    public String getAdditives() {
        return additives;
    }


    public void setIngredientsIdsDebug(List<String> ingredientsIdsDebug) {
        this.ingredientsIdsDebug = ingredientsIdsDebug;
    }
    public List<String> getIngredientsIdsDebug() {
        return ingredientsIdsDebug;
    }


    public void setAdditivesDebugTags(List<String> additivesDebugTags) {
        this.additivesDebugTags = additivesDebugTags;
    }
    public List<String> getAdditivesDebugTags() {
        return additivesDebugTags;
    }

}