CB.storage = {
  /**
   * key: required
   * value: required
   * transformationFunction: optional
   */
  save: function(key, value) {
    if (!key) {
      return false;
    }
    
    var data = value;
    
    data = JSON.stringify(data);
    CB.createCookie(key, data);
    return true;
  },
  
  /**
   * key: required
   * transformationFunction: optional
   */
  load: function(key) {
    var data = PT.readCookie(key);
    
    if (!data) {
      return null;
    }
    
    try {
      data = JSON.parse(data);
      
    } catch (e) {
      data = null;
    }
    
    return data;
  },
  
  update: function(key, value) {
    if (!key) {
      return false;
    }
    var data = this.load(key);
    
    if (!data) {
      data = {};
    }
    
    // Only update the data if both are objects, otherwise replace the data
    if (typeof data == 'object' && typeof data == typeof value) {
      $.extend(data, value);
    } else {
      data = value;
    }
    
    return this.save(key, data);
  },
  
  remove: function(key) {
    if (!key) {
        return false
    }
    
    this.save(key, null);
  }
};