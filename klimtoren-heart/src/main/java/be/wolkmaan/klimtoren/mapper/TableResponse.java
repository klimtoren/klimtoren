/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author karl
 */
@Data
@AllArgsConstructor
public class TableResponse<T> {

    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private T data;

    public static class Builder<T> {

        private int draw;
        private int recordsTotal;
        private int recordsFiltered;
        private T data;
        
        public Builder draw(int draw) {
            this.draw = draw;
            return this;
        }
        public Builder recordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
            return this;
        }
        public Builder recordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
            return this;
        }
        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public TableResponse build() {
            return new TableResponse(draw, recordsTotal, recordsFiltered, data);
        }
    }
}
