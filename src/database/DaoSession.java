package database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import my_components.comment.Comment;
import my_components.photo.Photo;
import my_components.rating.Rating;
import my_components.rating2comment.RatingToComment;

import my_components.comment.CommentDao;
import my_components.photo.PhotoDao;
import my_components.rating.RatingDao;
import my_components.rating2comment.RatingToCommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig commentDaoConfig;
    private final DaoConfig photoDaoConfig;
    private final DaoConfig ratingDaoConfig;
    private final DaoConfig ratingToCommentDaoConfig;

    private final CommentDao commentDao;
    private final PhotoDao photoDao;
    private final RatingDao ratingDao;
    private final RatingToCommentDao ratingToCommentDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        commentDaoConfig = daoConfigMap.get(CommentDao.class).clone();
        commentDaoConfig.initIdentityScope(type);

        photoDaoConfig = daoConfigMap.get(PhotoDao.class).clone();
        photoDaoConfig.initIdentityScope(type);

        ratingDaoConfig = daoConfigMap.get(RatingDao.class).clone();
        ratingDaoConfig.initIdentityScope(type);

        ratingToCommentDaoConfig = daoConfigMap.get(RatingToCommentDao.class).clone();
        ratingToCommentDaoConfig.initIdentityScope(type);

        commentDao = new CommentDao(commentDaoConfig, this);
        photoDao = new PhotoDao(photoDaoConfig, this);
        ratingDao = new RatingDao(ratingDaoConfig, this);
        ratingToCommentDao = new RatingToCommentDao(ratingToCommentDaoConfig, this);

        registerDao(Comment.class, commentDao);
        registerDao(Photo.class, photoDao);
        registerDao(Rating.class, ratingDao);
        registerDao(RatingToComment.class, ratingToCommentDao);
    }
    
    public void clear() {
        commentDaoConfig.getIdentityScope().clear();
        photoDaoConfig.getIdentityScope().clear();
        ratingDaoConfig.getIdentityScope().clear();
        ratingToCommentDaoConfig.getIdentityScope().clear();
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public PhotoDao getPhotoDao() {
        return photoDao;
    }

    public RatingDao getRatingDao() {
        return ratingDao;
    }

    public RatingToCommentDao getRatingToCommentDao() {
        return ratingToCommentDao;
    }

}
