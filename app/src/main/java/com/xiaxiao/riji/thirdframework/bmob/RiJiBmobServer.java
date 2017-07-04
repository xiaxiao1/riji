package com.xiaxiao.riji.thirdframework.bmob;

import android.content.Context;

import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.bean.WorkItem;
import com.xiaxiao.riji.util.UIDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiaxi on 2016/11/3.
 */
public class RiJiBmobServer {

    /*BmobListener mSuccessListener;
    BmobListener mErrorListener;*/
    BmobQuery mBmobQuery;
    UIDialog waitdialog;
    boolean enableDialog=true;
    Context context;

    public RiJiBmobServer(Context context) {
        this.context = context;
    }




    public void addDayWork(final DayWork dayWork, final BmobListener bmobListener) {
//        addListener(bmobListener);
//        showWaitDialog();
        dayWork.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
//                dismissWaitDialog();
                handleResult(objectId, e,bmobListener);

            }
        });
    }

    public void updateDayWork(final DayWork dayWork, final BmobListener bmobListener) {
//        addListener(bmobListener);
        dayWork.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                handleResult(dayWork, e,bmobListener);
            }
        });
    }

    public void getMyDayWorks(int limit, final BmobListener bmobListener) {
//        addListener(bmobListener);
        BmobQuery<DayWork> query = new BmobQuery<>();
        query.order("-createdAt");
//        query.include("workItems"); include can not use with BmobRelation
        if (limit>0) {
            query.setLimit(limit);
        }
        query.findObjects(new FindListener<DayWork>() {
            @Override
            public void done(List<DayWork> list, BmobException e) {
                handleResult(list,e,bmobListener);
            }
        });
    }


    public void addWorkItem(WorkItem workItem, final BmobListener b) {
//        addListener(b);
        workItem.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                handleResult(s,e,b);
            }
        });

    }

    public void updateWorkItem(final WorkItem workItem, final BmobListener bmobListener) {
//        addListener(bmobListener);
        workItem.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                handleResult(workItem, e,bmobListener);
            }
        });
    }

    public void findWorkItems(DayWork dayWork, final BmobListener bmobListener) {
//        addListener(bmobListener);
        BmobQuery<WorkItem> query = new BmobQuery<>();
        query.order("createdAt");
        query.addWhereRelatedTo("workItems", new BmobPointer(dayWork));
        query.findObjects(new FindListener<WorkItem>() {
            @Override
            public void done(List<WorkItem> list, BmobException e) {
                handleResult(list, e,bmobListener);
            }
        });
//
    }


    public void handleResult(Object obj, BmobException e,BmobListener bmobListener) {
        if (e == null) {
            handleSuccess(obj,bmobListener);
        } else {
            handleError(e,bmobListener);
        }
    }

/*


    public void addArticle(final Article article) {
        showWaitDialog();
        article.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                    dismissWaitDialog();
                    if (e == null) {
//                        book.setId(objectId);
                        handleSuccess(objectId);
                    } else {
                        handleError(e);
                    }

            }
        });
    }
    public void addArticle(final Article article, final BmobListener bmobListener) {
        addListener(bmobListener);
        addArticle(article);
    }

    public void addArticles(final List<BmobObject> articleList, final BmobListener bmobListener) {
        new BmobBatch().insertBatch(articleList).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            bmobListener.onSuccess(i);
                        }else{
                            bmobListener.onError(e);
                        }
                    }
                }else{
                    bmobListener.onError(e);
                }
            }
        });
    }

    public void deleteArticle(final Article article, final BmobListener bmobListener) {
        article.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    bmobListener.onSuccess(article);
                } else {
                    bmobListener.onError(e);
                }
            }
        });

    }

    public void updateArticle(final Article article) {
//        showWaitDialog();
        article.update(article.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    XiaoUtil.l("update ok.");
                    handleSuccess(article.getObjectId());
                } else {
                    XiaoUtil.l("update error:"+e.getMessage()+" errorCode:"+e.getErrorCode());
                    handleError(e);
                }
            }
        });
    }
    public void updateArticle(final Article article, final BmobListener bmobListener) {
        addListener(bmobListener);
        updateArticle(article);
    }





    public void getArticle( final BmobListener bmobListener) {
        int[] havecontentValue={0,1};
        BmobQuery<Article> b1 = new BmobQuery<>();
        BmobQuery<Article> b2 = new BmobQuery<>();
        List<BmobQuery<Article>> bs = new ArrayList<>();
        b1.addWhereNotEqualTo("contents", "");
        b2.addWhereLessThanOrEqualTo("havecontent", 1);
//        b2.addWhereContainedIn("havecontent", Arrays.asList(havecontentValue));
//        b2.addWhereEqualTo("havecontent", 1);
//        b2.addWhereEqualTo("mainContent", "");
        bs.add(b1);
        bs.add(b2);
        mBmobQuery = new BmobQuery<Article>();

//        mBmobQuery.addWhereNotContainedIn("havecontent", Arrays.asList(havecontentValue));
//        mBmobQuery.addWhereNotEqualTo("contents", "");
        mBmobQuery.and(bs);
//        mBmobQuery.order("-contents");
        mBmobQuery.setLimit(1);
        mBmobQuery.findObjects(new FindListener<Article>(){
            @Override
            public void done(List<Article> list, BmobException e) {
                if (e == null&&list.size()>0) {
                    bmobListener.onSuccess(list.get(0));
                } else {
                    bmobListener.onError(e);
                }
            }
        });
        */
/*mBmobQuery.getObject(id, new QueryListener<Article>() {
            @Override
            public void done(Article o, BmobException e) {
                if (e == null) {
                    bmobListener.onSuccess(o);
                } else {
                    bmobListener.onError(e);
                }
            }


        });*//*


    }


//    */
/**
//     * add o book
//     * @param book
//     * @param bmobListener
//     *//*

//    public void addBook(final BookBean book, final BmobListener bmobListener) {
//        addListener(bmobListener);
//        addBook(book);
//    }
//
//    */
/**
//     * update a book . useless now
//     * @param book
//     *//*

//    public void updateBook(final BookBean book) {
//        showWaitDialog();
//        book.update(book.getObjectId(), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                dismissWaitDialog();
//                if (e == null) {
//                    Util.L("update ok.");
//                    handleSuccess(book.getObjectId());
//                } else {
//                    Util.L("update error:"+e.getMessage()+" errorCode:"+e.getErrorCode());
//                    handleError(e);
//                }
//            }
//        });
//    }
//    public void updateBook(final BookBean book, final BmobListener bmobListener) {
//        addListener(bmobListener);
//        updateBook(book);
//    }
//
//    public void getBooks() {
//        if (mBmobQuery==null) {
//            return;
//        }
//       */
/* if (type!=BOOKTYPE_ALL) {
//            mBmobQuery.addWhereEqualTo("type", type);
//        }*//*

//        showWaitDialog();
//        mBmobQuery.findObjects(new FindListener<BookBean>() {
//            @Override
//            public void done(List<BookBean> list, BmobException e) {
//                dismissWaitDialog();
//                if (e == null) {
//                    Util.L("query ok.");
//                   handleSuccess(list);
//                } else {
//                    Util.L("query error:"+e.getMessage());
//                    handleError(e);
//                }
//            }
//        });
//    }
//    public void getBooksWithDefaultOptions(final BmobListener bmobListener) {
//        addListener(bmobListener);
//        mBmobQuery = new BmobQuery<BookBean>();
//        mBmobQuery.order("-createdAt");
//        mBmobQuery.addWhereEqualTo("showType", 1);
//        mBmobQuery.include("recommendPerson");
//
//        getBooks();
//    }
//
//    public void getMyBooks(final BmobListener bmobListener) {
//        addListener(bmobListener);
//        mBmobQuery = new BmobQuery<RelationShip>();
//        mBmobQuery.order("-createdAt");
//        if (Util.isLogin()) {
//            mBmobQuery.addWhereEqualTo("owner", Util.getUser());
//        } else {
//            mBmobQuery.addWhereEqualTo("owner", null);
//        }
//        mBmobQuery.include("book,book.recommendPerson");
//        mBmobQuery.findObjects(new FindListener<RelationShip>() {
//            @Override
//            public void done(List<RelationShip> list, BmobException e) {
//                if (e == null) {
//
//                    handleSuccess(list);
//                } else {
//                    handleError(e);
//                }
//            }
//        });
//    }


    */
/*public void getFamousWords() {
        if (mBmobQuery==null) {
            return;
        }
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<FamousWord>() {
            @Override
            public void done(List<FamousWord> list, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    Util.L("query ok.");
                    handleSuccess(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }
    public void  getFamousWordsWithDefaultOptions(final BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<>();
        mBmobQuery.setLimit(100);
        mBmobQuery.order("-createdAt");
        getFamousWords();
    }


    public void getAllIdeas(BmobListener bmobListener) {
        addListener(bmobListener);
        if (mBmobQuery==null) {
            mBmobQuery = new BmobQuery<BookNote>();
        }

        mBmobQuery.order("-createdAt");
        mBmobQuery.include("whoWrite,replyWhos,book,book.recommendPerson");
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    Util.L("query ok.");
                    handleSuccess(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }


    public void getBookNotes(BookBean book,BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<BookNote>();
        mBmobQuery.addWhereEqualTo("book", book);
        mBmobQuery.order("-createdAt");
        mBmobQuery.include("whoWrite,replyWhos,book");
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    Util.L("query ok.");
                    handleSuccess(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }

    *//*
*/
/**
     * add a book
     * @param bmobListener
     *//*
*/
/*
    public void addBookNote(final BookNote bookNote,BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bookNote.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
//                        book.setId(objectId);
                    handleSuccess(objectId);
                } else {
                    handleError(e);
                }

            }
        });
    }*//*


    public void getArticles(int skipNum,BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<Article>();
        BmobQuery<Article> q1 = new BmobQuery<Article>();
        mBmobQuery.addWhereEqualTo("contents", "");
        mBmobQuery.order("-createdAt");
//        mBmobQuery.addWhereNotEqualTo("havecontent",1);
        mBmobQuery.setLimit(100);
        mBmobQuery.setSkip(skipNum*100);
//        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
//                dismissWaitDialog();
                if (e == null) {
                    XiaoUtil.l("query ok.");
                    handleSuccess(list);
                } else {
                    XiaoUtil.l("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }

    public void getRealArticles(int skipNum,BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<Article>();
//        BmobQuery<Article> q1 = new BmobQuery<Article>();
        mBmobQuery.addWhereNotEqualTo("mainContent", "");
        mBmobQuery.order("-createdAt");
//        mBmobQuery.addWhereNotEqualTo("havecontent",1);
        mBmobQuery.setLimit(100);
        mBmobQuery.setSkip(skipNum*100);
//        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
//                dismissWaitDialog();
                if (e == null) {
                    XiaoUtil.l("query ok.");
                    handleSuccess(list);
                } else {
                    XiaoUtil.l("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }
    public void upFile(File file,int index,BmobListener bmobListener) {
        addListener(bmobListener);
//        showWaitDialog();
        final BmobFile bmobFile = new BmobFile(file);

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
//                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(bmobFile);
                } else {
                    handleError(e);
                }
            }
        });
    }
    */
/*public void updateBookCoverImage(File coverfile, final BookBean bookBean,BmobListener bmobListener) {
        addListener(bmobListener);
        final BmobFile bmobFile = new BmobFile(coverfile);
        showWaitDialog();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    bookBean.setCoverImage(bmobFile);
                    bookBean.update(bookBean.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dismissWaitDialog();
                            if (e == null) {
                                handleSuccess(1);
                            } else {
                                handleError(e);
                            }
                        }
                    });
                } else {
                    dismissWaitDialog();
                    handleError(e);
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
//                tv2.setText(""+value);
            }
        });
    }

    public void updateUserheadImage(File headfile, final MyUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        final BmobFile bmobFile = new BmobFile(headfile);
        showWaitDialog();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyUser myUser=bmobUser;
                    myUser.setHeadImage(bmobFile);
                    myUser.update(myUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dismissWaitDialog();
                            if (e == null) {
                                handleSuccess(1);
                            } else {
                                handleError(e);
                            }
                        }
                    });
                } else {
                    dismissWaitDialog();
                    handleError(null);
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
//                tv2.setText(""+value);
            }
        });
    }

    *//*
*/
/**
     * add a relationship
     *//*
*/
/*
    public void addRelationShip(final RelationShip relationShip) {
        showWaitDialog();
        relationShip.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(objectId);
                } else {
                    handleError(e);
                }

            }
        });
    }

    public void addRelationShip(final RelationShip relationShip, BmobListener bmobListener) {
        addListener(bmobListener);
        addRelationShip(relationShip);
    }*//*


    public void login(MyUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bmobUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser bmobUser, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    XiaoUtil.l("login ok");
                    handleSuccess(bmobUser);
                } else {
                    XiaoUtil.l("login error");
                    handleError(e);
                }
            }
        });
    }

    public void signUp(MyUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bmobUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser bmobUser, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(bmobUser);
                } else {
                    handleError(e);
                }
            }
        });
    }
*/

   /* public void getShelf(MyUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<RelationShip>();
        mBmobQuery.addWhereEqualTo("owner", bmobUser);
        mBmobQuery.include("book.objectId");
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<RelationShip>() {
            @Override
            public void done(List<RelationShip> list, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(list);
                } else {
                    handleError(e);
                }
            }
        });
    }


    public void updateRelationShip(final RelationShip relationShip) {
        showWaitDialog();
        relationShip.update(relationShip.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    handleSuccess(relationShip.getObjectId());
                } else {
                    handleError(e);
                }
            }
        });

    }

    public void updateRelationShip(RelationShip relationShip, BmobListener bmobListener) {
        addListener(bmobListener);
        updateRelationShip(relationShip);
    }


    public void deleteBook(final BookBean bookBean, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bookBean.delete(bookBean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(bookBean.getObjectId());
                } else {
                    handleError(e);
                }
            }
        });
    }

    public void deleteRelationShip(final RelationShip relationShip, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        relationShip.delete(relationShip.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(relationShip.getObjectId());
                } else {
                    handleError(e);
                }
            }
        });
    }

    public void sendFeedback(FeedBack feedBack, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        feedBack.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(s);
                } else {
                    handleError(e);
                }
            }
        });
    }


    public void countUsers(BmobListener bmobListener) {
        addListener(bmobListener);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.count(MyUser.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    handleSuccess(count);
                }else{
                    handleError(e);
                }
            }
        });
    }

    public void updateUser(final MyUser user, BmobListener bmobListener) {
        addListener(bmobListener);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null) {
                    handleSuccess(user.getObjectId());
                }else

                    handleError(e);
            }
        });
    }*/
    //*******************************************************************************************//


    /*protected void addListener(BmobListener bmobListener) {
        mSuccessListener=bmobListener;
        mErrorListener=bmobListener;
    }*/

    protected void handleSuccess(Object object,BmobListener bmobListener) {
        if (bmobListener!=null) {
            bmobListener.onSuccess(object);
        }
    }
    protected void handleError(BmobException e,BmobListener bmobListener) {
        if (bmobListener!=null) {
            bmobListener.onError(e);
        }
    }

    protected void showWaitDialog() {
        if (enableDialog) {
            waitdialog.showWaitDialog();
        }
    }
    protected  void dismissWaitDialog() {
        waitdialog.dismissWaitDialog();
    }
}
