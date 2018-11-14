//package com.opensense.dashboard.client.utils;
//
//import org.gwtbootstrap3.client.ui.ListBox;
//import org.gwtbootstrap3.client.ui.Modal;
//import org.gwtbootstrap3.client.ui.html.Span;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ChangeEvent;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.uibinder.client.UiBinder;
//import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.uibinder.client.UiHandler;
//import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.Image;
//import com.google.gwt.user.client.ui.Widget;
//
//public class Pager extends Composite{
//
//	private static final String STYLE_CLICKABLE = "clickable";
//	private static PagerUiBinder uiBinder = GWT.create(PagerUiBinder.class);
//	
//	interface PagerUiBinder extends UiBinder<Widget, Pager> {
//	}
//	
////	private final ITaskPortfolioViewModel viewModel;
//	//The different types how many objects are on one page
//	private String[] pageSize = {"2", "5", "10", "25", "50", "100"};
//	
////    @UiField
////    Image backwardsButton;
////    @UiField
////    Image forwardsButton;
////    @UiField
////    Span pageNumber;
////    @UiField
////    Image backwardsStepByStepButton;
////    @UiField
////    Image forwardsStepByStepButton;
////    @UiField
////    Modal pageConfigurationModal;
////    @UiField
////    org.gwtbootstrap3.client.ui.Button loadPageConfig;
////    @UiField
////    ListBox pagesListBox;
////    @UiField
////    Span hintForInternetExplorer;
//	
//	public Pager() {
////		initWidget(uiBinder.createAndBindUi(this));
////		this.viewModel = viewModel;
////		initConfigurationModal();
//	}
//	
//    @UiHandler("forwardsStepByStepButton")
//	public void onForwardsStepByStepButtonClicked(ClickEvent e){
//    	//If current page is the last do nothing 
////		if(((int) Math.ceil((double) viewModel.getShownObjectIds().size() / (double) viewModel.getMaxObjectsOnPage())) - 1 < viewModel.getObjectPage() + 1){
////			return;
////		}
////    	viewModel.setObjectPage(viewModel.getObjectPage() + 1);
////		viewModel.pagination();
//	}
//	
//	@UiHandler("backwardsStepByStepButton")
//	public void onBackwardsStepByStepButtonClicked(ClickEvent e){
//		//If current page is the first do nothing 
////		if(viewModel.getObjectPage() - 1 < 0){
////			return;
////		}
////		viewModel.setObjectPage(viewModel.getObjectPage() - 1);
////		viewModel.pagination();
//	}
//	@UiHandler("forwardsButton")
//	public void onForwardsButtonClicked(ClickEvent e){
////		//Goes to the last page
////		viewModel.setObjectPage(((int) Math.ceil((double) viewModel.getShownObjectIds().size() / (double) viewModel.getMaxObjectsOnPage())) - 1);
////		viewModel.pagination();
//	}
//	
//	@UiHandler("backwardsButton")
//	public void onBackwardsButtonClicked(ClickEvent e){
////		//Goes to the first page
////		viewModel.setObjectPage(0);
////		viewModel.pagination();
//	}
//	
//	@UiHandler("loadPageConfig")
//	public void onLoadPageConfigClicked(ClickEvent e){
////		viewModel.setMaxObjectsOnPage(Integer.parseInt(pagesListBox.getSelectedItemText()));
////		viewModel.setObjectPage(0);
////		viewModel.pagination();
////		viewModel.writeMaxObjectOnPageCookie();
////		pageConfigurationModal.hide();
//	}
//	
//	@UiHandler("pagesListBox")
//	public void onPagesListBoxChange(ChangeEvent e){
//		isIEAndHint();
////		if(!pagesListBox.getSelectedValue().equals(String.valueOf(viewModel.getMaxObjectsOnPage()))){
////			loadPageConfig.setEnabled(true);
////		}else{
////			loadPageConfig.setEnabled(false);
////		}
//	}
//	
//    public void setForwardsEnabled(final boolean enabled) {
////    	if(enabled){
////	    	forwardsButton.setUrl(GUIImageBundle.INSTANCE.forwardsBlue().getSafeUri().asString());
////	    	forwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.forwardsStepByStepBlue().getSafeUri().asString());
////	    	forwardsButton.addStyleName(STYLE_CLICKABLE);
////	    	forwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
////    	}else{
////    		forwardsButton.setUrl(GUIImageBundle.INSTANCE.forwards().getSafeUri().asString());
////	    	forwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.forwardsStepByStep().getSafeUri().asString());
////	    	forwardsButton.removeStyleName(STYLE_CLICKABLE);
////	    	forwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
////    	}
//    }
//    
//    public void setBackwardsEnabled(final boolean enabled) {
////    	if(enabled){
////    		backwardsButton.setUrl(GUIImageBundle.INSTANCE.backwardsBlue().getSafeUri().asString());
////    		backwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.backwardsStepbyStepBlue().getSafeUri().asString());
////    		backwardsButton.addStyleName(STYLE_CLICKABLE);
////        	backwardsStepByStepButton.addStyleName(STYLE_CLICKABLE);
////    	}else{
////    		backwardsButton.setUrl(GUIImageBundle.INSTANCE.backwards().getSafeUri().asString());
////    		backwardsStepByStepButton.setUrl(GUIImageBundle.INSTANCE.backwardsStepbyStep().getSafeUri().asString());
////    		backwardsButton.removeStyleName(STYLE_CLICKABLE);
////    		backwardsStepByStepButton.removeStyleName(STYLE_CLICKABLE);
////    	}
//    }
//    
//    public void setPage(String pageNumber){
//    	this.pageNumber.setText(pageNumber);
//    }
//    
//    /**
//     * makes the span clickable that the user can open the modal to configure how many objects should be on one page
//     */
//    public void initConfigurationModal(){
////    	pageNumber.addDomHandler(new ClickHandler(){
////			@Override
////			public void onClick(ClickEvent event) {
////				pagesListBox.clear();
////				isIEAndHint();
////				loadPageConfig.setEnabled(false);
////				int i = 0;
////				int selectedIndex = 0;
////		    	for(String page : pageSize){
////		    		if(page.equals(String.valueOf(viewModel.getMaxObjectsOnPage()))) {
////		    			selectedIndex = i;
////		    		}
////		    		pagesListBox.addItem(page);
////		    		i ++;
////		    	}
////		    	pagesListBox.setSelectedIndex(selectedIndex);
////				AppController.showModal(pageConfigurationModal);
////			}
////    	}, ClickEvent.getType());
//    }
//    
//    /**
//     * If the browser is IE and there are more than 10 objects on one page shows a hint 
//     */
//    public void isIEAndHint(){
////		if(AppController.isIE() && (pagesListBox.getSelectedValue() != null && Integer.valueOf(pagesListBox.getSelectedValue()) > 10 || viewModel.getMaxObjectsOnPage() > 10)){
////			hintForInternetExplorer.setVisible(true);
////		}else{
////			hintForInternetExplorer.setVisible(false);
////		}
//    }
//}
